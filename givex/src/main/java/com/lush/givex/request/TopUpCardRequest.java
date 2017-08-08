package com.lush.givex.request;

import com.android.volley.Response;
import com.lush.givex.Environment;
import com.lush.givex.base.BaseGivexRequest;
import com.lush.givex.model.request.BasicRequestData;
import com.lush.givex.model.response.TopUpCardResponse;

/**
 * @author Matt Allen
 */
public class TopUpCardRequest extends BaseGivexRequest<TopUpCardResponse>
{
	public TopUpCardRequest(BasicRequestData data, String baseUrl, Response.Listener<TopUpCardResponse> listener, Response.ErrorListener errorListener)
	{
		super(Method.POST, data, baseUrl, listener, errorListener);
	}

	@Override
	protected TopUpCardResponse createResponse(String networkResponse)
	{
		TopUpCardResponse response = new TopUpCardResponse();
		response.fromNetworkResponse(networkResponse);
		return response;
	}
}
