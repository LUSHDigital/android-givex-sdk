package com.lush.givex.fallback;

import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

public final class GivexErrorListener implements Response.ErrorListener {
    private final Response.ErrorListener errorListener;
    private final GivexTimeoutErrorListener timeoutErrorListener;

    public GivexErrorListener(Response.ErrorListener errorListener, GivexTimeoutErrorListener timeoutErrorListener) {
        this.errorListener = errorListener;
        this.timeoutErrorListener = timeoutErrorListener;
    }

    @Override
    public final void onErrorResponse(VolleyError error) {
        if (error instanceof TimeoutError) {
            timeoutErrorListener.onTimeoutErrorResponse((TimeoutError)error);
        } else {
            errorListener.onErrorResponse(error);
        }
    }
}