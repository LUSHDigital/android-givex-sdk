package com.lush.givex.fallback.impl;

import com.android.volley.Response;
import com.android.volley.VolleyError;

final class GivexErrorInterceptor implements Response.ErrorListener {
    private final Response.ErrorListener callerErrorListener;
    private final Response.ErrorListener parentErrorListener;
    private final boolean propagateError;

    GivexErrorInterceptor(Response.ErrorListener callerErrorListener, Response.ErrorListener parentErrorListener, boolean propagateError) {
        this.callerErrorListener = callerErrorListener;
        this.parentErrorListener = parentErrorListener;
        this.propagateError = propagateError;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        parentErrorListener.onErrorResponse(error);
        if (propagateError) {
            callerErrorListener.onErrorResponse(error);
        }
    }
}