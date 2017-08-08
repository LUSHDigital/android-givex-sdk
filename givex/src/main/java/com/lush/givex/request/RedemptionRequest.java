package com.lush.givex.request;

import com.android.volley.Response;
import com.lush.givex.Environment;
import com.lush.givex.base.BaseGivexRequest;
import com.lush.givex.model.request.BasicRequestData;
import com.lush.givex.model.response.RedemptionResponse;

/**
 * @author Matt Allen
 */
public class RedemptionRequest extends BaseGivexRequest<RedemptionResponse>
{
	public RedemptionRequest(BasicRequestData data, String baseUrl, Response.Listener<RedemptionResponse> listener, Response.ErrorListener errorListener)
	{
		super(Method.POST, data, baseUrl, listener, errorListener);
	}

	@Override
	protected RedemptionResponse createResponse(String networkResponse)
	{
		RedemptionResponse response = new RedemptionResponse();
		response.fromNetworkResponse(networkResponse);
		return response;
	}
}
