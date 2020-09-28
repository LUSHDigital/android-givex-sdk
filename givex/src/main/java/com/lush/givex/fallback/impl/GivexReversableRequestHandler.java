package com.lush.givex.fallback.impl;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.lush.givex.fallback.GivexRequestFactory;
import com.lush.givex.fallback.GivexTimeoutErrorListener;
import com.lush.givex.model.request.BasicRequestData;
import com.lush.givex.model.response.ReversalResponse;
import com.lush.givex.request.ReversalRequest;

/**
 * This request handler implements the Givex single request flow. The request will be sent and in case
 * of a timeout a Givex request reversal is send and the parent notified. All non-timeout errors are
 * communicated straight to the caller. Errors in reversals are not communicated to the caller.
 *
 * @param <R> the response type of the responses returned by requests handled by this handler.
 */
final class GivexReversableRequestHandler<R> implements GivexTimeoutErrorListener, Response.Listener<ReversalResponse>, Response.ErrorListener {
    private final RequestQueue queue;
    private final int timeoutMillis;
    private final String baseUrl;
    private final boolean propagateTimeoutError;
    private final String name;
    private final GivexTimeoutErrorListener parentListener;

    private ReversalRequest requestReversal;

    GivexReversableRequestHandler(RequestQueue queue, int timeoutMillis, String baseUrl, boolean propagateTimeoutError, String name, GivexTimeoutErrorListener parentListener) {
        this.queue = queue;
        this.timeoutMillis = timeoutMillis;
        this.baseUrl = baseUrl;
        this.propagateTimeoutError = propagateTimeoutError;
        this.name = name;
        this.parentListener = parentListener;
    }

    void send(BasicRequestData data, GivexRequestFactory<R> requestFactory, Response.Listener<R> listener, Response.ErrorListener errorListener) {
        final GivexErrorListener requestErrorListener = new GivexErrorListener(errorListener, this, propagateTimeoutError);
        final Request<R> request = requestFactory.buildRequest(data, baseUrl, timeoutMillis, listener, requestErrorListener);

        final BasicRequestData reversalData = data.getReversalData();
        requestReversal = new ReversalRequest(reversalData, baseUrl, this, this);

        queue.add(request);
    }

    @Override
    public void onTimeoutErrorResponse(TimeoutError timeoutError) {
        Log.d(getClass().getSimpleName(), buildLogMessageStart() + "timed out.");

        queue.add(requestReversal);
        parentListener.onTimeoutErrorResponse(timeoutError);
    }

    /**
     * This listener will only receive reversal notifications. We are not interested in propagating such notifications.
     */
    @Override
    public void onResponse(ReversalResponse response) {
        Log.d(getClass().getSimpleName(), buildLogMessageStart() + "reversal did not have an error response.");
    }

    /**
     * This listener will only receive the errors from reversals. We are not interested in propagating such errors.
     */
    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e(getClass().getSimpleName(), buildLogMessageStart() + "reversal error: " + error.getMessage(), error);
    }

    private String buildLogMessageStart() {
        final String type = propagateTimeoutError ? "Secondary " : "Primary ";

        return type + name + " request ";
    }
}
