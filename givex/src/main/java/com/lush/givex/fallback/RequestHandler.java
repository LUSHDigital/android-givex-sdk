package com.lush.givex.fallback;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.lush.givex.model.request.BasicRequestData;
import com.lush.givex.model.response.ReversalResponse;
import com.lush.givex.request.ReversalRequest;

/**
 * This request handler implements the Givex request flow. For a single call a primary and a fallback
 * URL is designated. The primary URL will be tried first and in case of a timeout a Givex request
 * reversal is send to the primary URL and the request is send to the fallback URL. In case of timeout
 * of the fallback URL, a Givex request reversal is sent to the fallback URL too.
 *
 * @param <R> the response type of the responses returned by requests handled by this handler.
 */
public final class RequestHandler<R> implements GivexTimeoutErrorListener, Response.Listener<ReversalResponse>, Response.ErrorListener {
    private final RequestQueue queue;
    private final int timeoutMillis;
    private final String baseUrl, fallbackUrl;

    private Request<R> request;
    private ReversalRequest requestReversal;

    private Request<R> fallbackRequest;
    private ReversalRequest fallbackRequestReversal;

    public RequestHandler(RequestQueue queue, int timeoutMillis, String baseUrl, String fallbackUrl) {
        this.queue = queue;
        this.timeoutMillis = timeoutMillis;
        this.baseUrl = baseUrl;
        this.fallbackUrl = fallbackUrl;
    }

    public void send(BasicRequestData data, GivexRequestFactory<R> requestFactory, Response.Listener<R> listener, Response.ErrorListener errorListener) {
        final GivexErrorListener givexErrorListener = new GivexErrorListener(errorListener, this);
        request = requestFactory.buildRequest(data, baseUrl, timeoutMillis, listener, givexErrorListener);
        fallbackRequest = requestFactory.buildRequest(data, fallbackUrl, timeoutMillis, listener, givexErrorListener);

        final BasicRequestData reversalData = data.getReversalData();
        requestReversal = new ReversalRequest(reversalData, baseUrl, this, this);
        fallbackRequestReversal = new ReversalRequest(reversalData, fallbackUrl, this, this);

        queue.add(request);
    }

    @Override
    public void onTimeoutErrorResponse(TimeoutError timeoutError) {
        if (request != null) {
            request = null;
            queue.add(requestReversal);
            queue.add(fallbackRequest);
        } else {
            fallbackRequest = null;
            queue.add(fallbackRequestReversal);
        }
    }

    /**
     * This listener will only receive reversal notifications. We are not interested in propagating such notifications.
     */
    @Override
    public void onResponse(ReversalResponse response) {
        if (requestReversal != null) {
            requestReversal = null;
            Log.d(getClass().getName(), "Reversal of primary request succeeded.");
        } else {
            fallbackRequestReversal = null;
            Log.d(getClass().getName(), "Reversal of fallback request succeeded.");
        }
    }

    /**
     * This listener will only receive the errors from reversals. We are not interested in propagating such errors.
     */
    @Override
    public void onErrorResponse(VolleyError error) {
        if (requestReversal != null) {
            requestReversal = null;
            Log.e(getClass().getName(), "Reversal of primary request failed.", error);
        } else {
            fallbackRequestReversal = null;
            Log.e(getClass().getName(), "Reversal of fallback request failed.", error);
        }
    }
}
