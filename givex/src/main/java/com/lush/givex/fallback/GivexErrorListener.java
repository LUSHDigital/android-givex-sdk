package com.lush.givex.fallback;

import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

public final class GivexErrorListener implements Response.ErrorListener {
    private final Response.ErrorListener errorListener;
    private final GivexTimeoutErrorListener timeoutErrorListener;
    private final boolean propagateTimeoutError;

    public GivexErrorListener(Response.ErrorListener errorListener, GivexTimeoutErrorListener timeoutErrorListener, boolean propagateTimeoutError) {
        this.errorListener = errorListener;
        this.timeoutErrorListener = timeoutErrorListener;
        this.propagateTimeoutError = propagateTimeoutError;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if (error instanceof TimeoutError) {
            onTimeoutErrorResponse((TimeoutError)error);
        } else {
            errorListener.onErrorResponse(error);
        }
    }

    private void onTimeoutErrorResponse(TimeoutError error) {
        timeoutErrorListener.onTimeoutErrorResponse(error);
        if (propagateTimeoutError) {
            errorListener.onErrorResponse(error);
        }
    }
}