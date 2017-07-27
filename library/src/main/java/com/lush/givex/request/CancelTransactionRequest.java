package com.lush.givex.request;

import com.android.volley.Response;
import com.lush.givex.Environment;
import com.lush.givex.base.BaseGivexRequest;
import com.lush.givex.model.request.BasicRequestData;
import com.lush.givex.model.response.CancelTransactionResponse;

/**
 * @author Matt Allen
 */
public class CancelTransactionRequest extends BaseGivexRequest<CancelTransactionResponse>
{
	public CancelTransactionRequest(BasicRequestData data, Environment environment, Response.Listener<CancelTransactionResponse> listener, Response.ErrorListener errorListener)
	{
		super(Method.POST, data, environment, listener, errorListener);
	}

	@Override
	protected CancelTransactionResponse createResponse(String networkResponse)
	{
		CancelTransactionResponse response = new CancelTransactionResponse();
		response.fromNetworkResponse(networkResponse);
		return response;
	}
}
