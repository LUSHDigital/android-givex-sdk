package com.lush.givex.request;

import com.android.volley.Response;
import com.lush.givex.base.BaseGivexRequest;
import com.lush.givex.model.request.BasicRequestData;
import com.lush.givex.model.response.RedemptionResponse;

/**
 * @author Matt Allen
 */
public final class RedemptionRequest extends BaseGivexRequest<RedemptionResponse>
{
	public RedemptionRequest(BasicRequestData data, String baseUrl, int timeoutMillis, Response.Listener<RedemptionResponse> listener, Response.ErrorListener errorListener) {
		super(Method.POST, data, baseUrl, timeoutMillis, listener, errorListener);
	}

	@Override
	protected RedemptionResponse createResponse(String networkResponse) {
		final RedemptionResponse response = new RedemptionResponse();
		response.fromNetworkResponse(networkResponse);

		return response;
	}
}
