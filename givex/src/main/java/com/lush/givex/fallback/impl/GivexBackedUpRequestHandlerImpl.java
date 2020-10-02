package com.lush.givex.fallback.impl;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lush.givex.fallback.GivexBackedUpRequestHandler;
import com.lush.givex.fallback.GivexRequestFactory;
import com.lush.givex.model.request.BasicRequestData;

/**
 * This request handler implements the Givex request flow. For a single call a primary and a secondary
 * URL is designated. The primary URL will be tried first and in case of a network error a Givex request
 * reversal is send to the primary URL and the request is send to the secondary URL. In case of a network
 * error of the secondary URL, a Givex request reversal is sent to the secondary URL too. Network errors are
 * only communicated to the caller when the secondary URL is used. Errors in reversals are not communicated
 * to the caller. For non reversible requests, the flow is the same but no reversals are sent.
 *
 * @param <R> the response type of the responses returned by requests handled by this handler.
 */
public final class GivexBackedUpRequestHandlerImpl<R> implements GivexBackedUpRequestHandler<R> {
    private final RequestQueue queue;
    private final int timeoutMillis;
    private final GivexUrl primaryUrl, secondaryUrl;

    public GivexBackedUpRequestHandlerImpl(RequestQueue queue, int timeoutMillis, String primaryUrl, String secondaryUrl) {
        this.queue = queue;
        this.timeoutMillis = timeoutMillis;
        this.primaryUrl = new GivexUrl(primaryUrl, true);
        this.secondaryUrl = new GivexUrl(secondaryUrl, false);
    }

    @Override
    public void send(final BasicRequestData data, final GivexRequestFactory<R> requestFactory, final Response.Listener<R> listener, final Response.ErrorListener errorListener) {
        if (data.isReversible()) {
            sendReversible(data, requestFactory, listener, errorListener);
        } else {
            sendNonReversible(data, requestFactory, listener, errorListener);
        }
    }

    private void sendReversible(final BasicRequestData data, final GivexRequestFactory<R> requestFactory, final Response.Listener<R> listener, final Response.ErrorListener errorListener) {
        Log.d(getClass().getSimpleName(), "Sending primary " + data.name() + " request to " + primaryUrl + " ...");
        final GivexReversibleRequestHandler<R> primary = buildPrimaryReversibleRequestHandler(data, requestFactory, listener, errorListener);
        primary.send(data, requestFactory, listener);
    }

    private GivexReversibleRequestHandler<R> buildPrimaryReversibleRequestHandler(final BasicRequestData data, final GivexRequestFactory<R> requestFactory, final Response.Listener<R> listener, final Response.ErrorListener errorListener) {
        final GivexReversibleRequestHandler<R> secondary = new GivexReversibleRequestHandler<R>(queue, timeoutMillis, secondaryUrl, data.name(), errorListener);
        final Response.ErrorListener primaryLocalErrorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(getClass().getSimpleName(), "Sending secondary " + data.name() + " request (after primary network error) to " + secondaryUrl + " ...");
                secondary.send(data, requestFactory, listener);
            }
        };

        return new GivexReversibleRequestHandler<R>(queue, timeoutMillis, primaryUrl, data.name(), primaryLocalErrorListener);
    }

    private void sendNonReversible(final BasicRequestData data, final GivexRequestFactory<R> requestFactory, final Response.Listener<R> listener, final Response.ErrorListener errorListener) {
        Log.d(getClass().getSimpleName(), "Sending primary " + data.name() + " request to " + primaryUrl + " ...");
        final Request<R> primary = buildPrimaryNonReversibleRequest(data, requestFactory, listener, errorListener);
        queue.add(primary);
    }

    private Request<R> buildPrimaryNonReversibleRequest(final BasicRequestData data, final GivexRequestFactory<R> requestFactory, final Response.Listener<R> listener, final Response.ErrorListener errorListener) {
        final Request<R> secondary = requestFactory.buildRequest(data, secondaryUrl.url, timeoutMillis, listener, errorListener);
        final Response.ErrorListener primaryLocalErrorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(getClass().getSimpleName(), "Sending secondary " + data.name() + " request (after primary network error) to " + secondaryUrl + " ...");
                queue.add(secondary);
            }
        };

        return requestFactory.buildRequest(data, primaryUrl.url, timeoutMillis, listener, primaryLocalErrorListener);
    }
}
