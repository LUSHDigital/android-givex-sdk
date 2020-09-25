package com.lush.givex.request.factories;

import com.android.volley.Request;
import com.android.volley.Response;
import com.lush.givex.fallback.GivexRequestFactory;
import com.lush.givex.model.request.BasicRequestData;
import com.lush.givex.model.response.RedemptionResponse;
import com.lush.givex.request.RedemptionRequest;

public final class RedemptionRequestFactory implements GivexRequestFactory<RedemptionResponse> {

    @Override
    public Request<RedemptionResponse> buildRequest(BasicRequestData data, String baseUrl, int timeoutMillis, Response.Listener<RedemptionResponse> listener, Response.ErrorListener errorListener) {
        return new RedemptionRequest(data, baseUrl, timeoutMillis, listener, errorListener);
    }
}