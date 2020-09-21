package com.lush.givex.request;

import com.android.volley.Response;
import com.lush.givex.base.BaseGivexRequest;
import com.lush.givex.model.request.BasicRequestData;
import com.lush.givex.model.response.CancelTransactionResponse;

/**
 * @author Matt Allen
 */
public final class CancelTransactionRequest extends BaseGivexRequest<CancelTransactionResponse> {

	public CancelTransactionRequest(BasicRequestData data, String baseUrl, int timeoutMillis, Response.Listener<CancelTransactionResponse> listener, Response.ErrorListener errorListener) {
		super(Method.POST, data, baseUrl, timeoutMillis, listener, errorListener);
	}

	@Override
	protected CancelTransactionResponse createResponse(String networkResponse) {
		final CancelTransactionResponse response = new CancelTransactionResponse();
		response.fromNetworkResponse(networkResponse);

		return response;
	}
}
