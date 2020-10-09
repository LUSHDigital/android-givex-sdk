package com.lush.givex.fallback.impl;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lush.givex.fallback.GivexRequestFactory;
import com.lush.givex.model.request.BasicRequestData;
import com.lush.givex.model.response.ReversalResponse;
import com.lush.givex.request.ReversalRequest;

/**
 * This request handler implements the Givex reversible request flow. The request will be sent and in case
 * of a network error a Givex request reversal is send and the parent notified. Errors in reversals are
 * logged but not communicated to the caller.
 *
 * @param <R> the response type of the responses returned by requests handled by this handler.
 */
final class GivexReversibleRequestHandler<R> implements Response.Listener<ReversalResponse>, Response.ErrorListener {
    private final RequestQueue queue;
    private final int timeoutMillis;
    private final GivexUrl url;
    private final String name;
    private final Response.ErrorListener parentErrorListener;

    private ReversalRequest requestReversal;

    GivexReversibleRequestHandler(RequestQueue queue, int timeoutMillis, GivexUrl url, String name, Response.ErrorListener parentErrorListener) {
        this.queue = queue;
        this.timeoutMillis = timeoutMillis;
        this.url = url;
        this.name = name;
        this.parentErrorListener = parentErrorListener;
    }

    void send(BasicRequestData data, GivexRequestFactory<R> requestFactory, Response.Listener<R> listener) {
        final Response.ErrorListener localErrorListener = buildLocalErrorListener();
        final Request<R> request = requestFactory.buildRequest(data, url.url, timeoutMillis, listener, localErrorListener);

        final BasicRequestData reversalData = data.getReversalData();
        requestReversal = new ReversalRequest(reversalData, url.url, this, this);

        queue.add(request);
    }

    private Response.ErrorListener buildLocalErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(getClass().getSimpleName(), buildLogMessageStart() + " returned a network error (" + error.getMessage() + "). Will send a reversal.");

                queue.add(requestReversal);
                parentErrorListener.onErrorResponse(error);
            }
        };
    }

    /**
     * This listener will only receive reversal notifications. We are not interested in propagating such notifications.
     */
    @Override
    public void onResponse(ReversalResponse response) {
        if (response.isSuccess()) {
            Log.d(getClass().getSimpleName(), buildLogMessageStart() + "reversal was successful.");
        } else {
            Log.e(getClass().getSimpleName(), buildLogMessageStart() + "reversal failed with a Givex error code: " + response.getError());
        }
    }

    /**
     * This listener will only receive the errors from reversals. We are not interested in propagating such errors.
     */
    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e(getClass().getSimpleName(), buildLogMessageStart() + "reversal network error: " + error.getMessage(), error);
    }

    private String buildLogMessageStart() {
        final String type = url.isPrimary ? "Primary " : "Secondary ";

        return type + name + " request ";
    }
}
