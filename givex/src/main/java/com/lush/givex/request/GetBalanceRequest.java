package com.lush.givex.request;

import com.android.volley.Response;
import com.lush.givex.Environment;
import com.lush.givex.base.BaseGivexRequest;
import com.lush.givex.model.request.BasicRequestData;
import com.lush.givex.model.response.GetBalanceResponse;

/**
 * @author Matt Allen
 */
public class GetBalanceRequest extends BaseGivexRequest<GetBalanceResponse>
{
	public GetBalanceRequest(BasicRequestData data, String baseUrl, Response.Listener<GetBalanceResponse> listener, Response.ErrorListener errorListener)
	{
		super(Method.POST, data, baseUrl, listener, errorListener);
	}

	@Override
	protected GetBalanceResponse createResponse(String networkResponse)
	{
		GetBalanceResponse response = new GetBalanceResponse();
		response.fromNetworkResponse(networkResponse);
		return response;
	}
}
