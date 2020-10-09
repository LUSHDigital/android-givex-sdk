package com.lush.givex.request.factories;

import com.android.volley.Request;
import com.android.volley.Response;
import com.lush.givex.fallback.GivexRequestFactory;
import com.lush.givex.model.request.BasicRequestData;
import com.lush.givex.model.response.GetBalanceResponse;
import com.lush.givex.request.GetBalanceRequest;

public final class GetBalanceRequestFactory implements GivexRequestFactory<GetBalanceResponse> {

    @Override
    public Request<GetBalanceResponse> buildRequest(BasicRequestData data, String baseUrl, int timeoutMillis, Response.Listener<GetBalanceResponse> listener, Response.ErrorListener errorListener) {
        return new GetBalanceRequest(data, baseUrl, timeoutMillis, listener, errorListener);
    }
}