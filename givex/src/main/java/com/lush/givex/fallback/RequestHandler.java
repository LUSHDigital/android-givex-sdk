package com.lush.givex.fallback;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.lush.givex.model.request.BasicRequestData;
import com.lush.givex.model.response.ReversalResponse;
import com.lush.givex.request.ReversalRequest;

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
            //TODO Log main reversal success
        } else {
            fallbackRequestReversal = null;
            //TODO Log fallback reversal success
        }
    }

    /**
     * This listener will only receive the errors from reversals. We are not interested in propagating such errors.
     */
    @Override
    public void onErrorResponse(VolleyError error) {
        if (requestReversal != null) {
            requestReversal = null;
            //TODO Log main reversal error
        } else {
            fallbackRequestReversal = null;
            //TODO Log fallback reversal error
        }
    }
}
