package com.lush.givex.request;

import com.android.volley.Response;
import com.lush.givex.Environment;
import com.lush.givex.base.BaseGivexRequest;
import com.lush.givex.model.request.ActivateCardRequestData;
import com.lush.givex.model.response.ActivateCardResponse;

/**
 * @author Matt Allen
 */
public class ActivateCardRequest extends BaseGivexRequest<ActivateCardResponse>
{
	public ActivateCardRequest(Environment environment, int method, ActivateCardRequestData data, Response.Listener<ActivateCardResponse> listener, Response.ErrorListener errorListener)
	{
		super(method, data, environment, listener, errorListener);
	}

	@Override
	protected ActivateCardResponse createResponse(String networkResponse)
	{
		ActivateCardResponse response = new ActivateCardResponse();
		response.fromNetworkResponse(networkResponse);
		return response;
	}
}
