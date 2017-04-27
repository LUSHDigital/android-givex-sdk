package com.lush.givex.request;

import com.android.volley.Response;
import com.lush.givex.Environment;
import com.lush.givex.base.BaseGivexRequest;
import com.lush.givex.model.request.BasicRequestData;
import com.lush.givex.model.response.CancelCardResponse;

/**
 * @author Matt Allen
 */
public class CancelCardRequest extends BaseGivexRequest<CancelCardResponse>
{
	public CancelCardRequest(BasicRequestData data, Environment environment, Response.Listener<CancelCardResponse> listener, Response.ErrorListener errorListener)
	{
		super(Method.POST, data, environment, listener, errorListener);
	}

	@Override
	protected CancelCardResponse createResponse(String networkResponse)
	{
		CancelCardResponse response = new CancelCardResponse();
		response.fromNetworkResponse(networkResponse);
		return response;
	}
}
