package com.lush.givex.fallback;

import com.android.volley.Response;
import com.lush.givex.model.request.BasicRequestData;

public interface GivexBackedUpRequestHandler<R> {

    void send(final BasicRequestData data, final GivexRequestFactory<R> requestFactory, final Response.Listener<R> listener, final Response.ErrorListener errorListener);
}
