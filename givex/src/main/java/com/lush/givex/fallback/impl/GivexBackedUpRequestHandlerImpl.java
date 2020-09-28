package com.lush.givex.fallback.impl;

import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.lush.givex.fallback.GivexBackedUpRequestHandler;
import com.lush.givex.fallback.GivexRequestFactory;
import com.lush.givex.fallback.GivexTimeoutErrorListener;
import com.lush.givex.model.request.BasicRequestData;

/**
 * This request handler implements the Givex request flow. For a single call a primary and a fallback
 * URL is designated. The primary URL will be tried first and in case of a timeout a Givex request
 * reversal is send to the primary URL and the request is send to the fallback URL. In case of timeout
 * of the fallback URL, a Givex request reversal is sent to the fallback URL too. All non-timeout errors
 * are communicated straight to the caller. A timeout error in the fallback request is also communicated
 * to the caller. Errors in reversals are not communicated to the caller.
 *
 * @param <R> the response type of the responses returned by requests handled by this handler.
 */
public final class GivexBackedUpRequestHandlerImpl<R> implements GivexBackedUpRequestHandler<R> {
    private final RequestQueue queue;
    private final int timeoutMillis;
    private final String baseUrl, fallbackUrl;

    public GivexBackedUpRequestHandlerImpl(RequestQueue queue, int timeoutMillis, String baseUrl, String fallbackUrl) {
        this.queue = queue;
        this.timeoutMillis = timeoutMillis;
        this.baseUrl = baseUrl;
        this.fallbackUrl = fallbackUrl;
    }

    @Override
    public void send(final BasicRequestData data, final GivexRequestFactory<R> requestFactory, final Response.Listener<R> listener, final Response.ErrorListener errorListener) {
        final GivexReversableRequestHandler<R> secondary = buildSecondaryRequest(data.name());
        final GivexReversableRequestHandler<R> primary = buildPrimaryRequest(secondary, data, requestFactory, listener, errorListener);

        Log.d(getClass().getSimpleName(), "Sending primary " + data.name() + " request ...");
        primary.send(data, requestFactory, listener, errorListener);
    }

    private GivexReversableRequestHandler<R> buildPrimaryRequest(final GivexReversableRequestHandler<R> secondary, final BasicRequestData data, final GivexRequestFactory<R> requestFactory, final Response.Listener<R> listener, final Response.ErrorListener errorListener) {
        final GivexTimeoutErrorListener primaryListener = new GivexTimeoutErrorListener() {
            @Override
            public void onTimeoutErrorResponse(TimeoutError timeoutError) {
                Log.d(getClass().getSimpleName(), "Sending secondary " + data.name() + " request (after primary timeout) ...");
                secondary.send(data, requestFactory, listener, errorListener);
            }
        };

        return new GivexReversableRequestHandler<R>(queue, timeoutMillis, baseUrl, false, data.name(), primaryListener);
    }

    private GivexReversableRequestHandler<R> buildSecondaryRequest(final String name) {
        final GivexTimeoutErrorListener secondaryListener = new GivexTimeoutErrorListener() {
            @Override
            public void onTimeoutErrorResponse(TimeoutError timeoutError) {
                Log.e(getClass().getSimpleName(), "Secondary " + name + " request time out.");
            }
        };

        return new GivexReversableRequestHandler<R>(queue, timeoutMillis, fallbackUrl, true, name, secondaryListener);
    }
}
