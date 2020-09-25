package com.lush.givex.request.factories;

import com.android.volley.Request;
import com.android.volley.Response;
import com.lush.givex.fallback.GivexRequestFactory;
import com.lush.givex.model.request.BasicRequestData;
import com.lush.givex.model.response.TopUpCardResponse;
import com.lush.givex.request.TopUpCardRequest;

public final class TopUpCardRequestFactory implements GivexRequestFactory<TopUpCardResponse> {

    @Override
    public Request<TopUpCardResponse> buildRequest(BasicRequestData data, String baseUrl, int timeoutMillis, Response.Listener<TopUpCardResponse> listener, Response.ErrorListener errorListener) {
        return new TopUpCardRequest(data, baseUrl, timeoutMillis, listener, errorListener);
    }
}