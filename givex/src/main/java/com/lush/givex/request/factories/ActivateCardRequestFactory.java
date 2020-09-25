package com.lush.givex.request.factories;

import com.android.volley.Request;
import com.android.volley.Response;
import com.lush.givex.fallback.GivexRequestFactory;
import com.lush.givex.model.request.BasicRequestData;
import com.lush.givex.model.response.ActivateCardResponse;
import com.lush.givex.request.ActivateCardRequest;

public final class ActivateCardRequestFactory implements GivexRequestFactory<ActivateCardResponse> {

    @Override
    public Request<ActivateCardResponse> buildRequest(BasicRequestData data, String baseUrl, int timeoutMillis, Response.Listener<ActivateCardResponse> listener, Response.ErrorListener errorListener) {
        return new ActivateCardRequest(baseUrl, data, timeoutMillis, listener, errorListener);
    }
}