package com.lush.givex.request.factories;

import com.android.volley.Request;
import com.android.volley.Response;
import com.lush.givex.fallback.GivexRequestFactory;
import com.lush.givex.model.request.BasicRequestData;
import com.lush.givex.model.response.CancelTransactionResponse;
import com.lush.givex.request.CancelTransactionRequest;

public final class CancelTransactionRequestFactory implements GivexRequestFactory<CancelTransactionResponse> {

    @Override
    public Request<CancelTransactionResponse> buildRequest(BasicRequestData data, String baseUrl, int timeoutMillis, Response.Listener<CancelTransactionResponse> listener, Response.ErrorListener errorListener) {
        return new CancelTransactionRequest(data, baseUrl, timeoutMillis, listener, errorListener);
    }
}