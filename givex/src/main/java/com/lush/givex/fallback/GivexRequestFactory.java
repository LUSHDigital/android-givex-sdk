package com.lush.givex.fallback;

import com.android.volley.Request;
import com.android.volley.Response;
import com.lush.givex.model.request.BasicRequestData;

public interface GivexRequestFactory<R> {

    Request<R> buildRequest(BasicRequestData data, String baseUrl, int timeoutMillis, Response.Listener<R> listener, Response.ErrorListener errorListener);
}
