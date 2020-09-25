package com.lush.givex.request.factories;

import com.android.volley.Request;
import com.android.volley.Response;
import com.lush.givex.fallback.GivexRequestFactory;
import com.lush.givex.model.request.BasicRequestData;
import com.lush.givex.model.response.CashBackResponse;
import com.lush.givex.request.CashBackRequest;

public final class CashBackRequestFactory implements GivexRequestFactory<CashBackResponse> {

    @Override
    public Request<CashBackResponse> buildRequest(BasicRequestData data, String baseUrl, int timeoutMillis, Response.Listener<CashBackResponse> listener, Response.ErrorListener errorListener) {
        return new CashBackRequest(data, baseUrl, timeoutMillis, listener, errorListener);
    }
}