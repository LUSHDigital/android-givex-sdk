package com.lush.givex.request;

import com.android.volley.Response;
import com.lush.givex.base.BaseGivexRequest;
import com.lush.givex.model.request.BasicRequestData;
import com.lush.givex.model.response.GetBalanceResponse;

/**
 * @author Matt Allen
 */
public final class GetBalanceRequest extends BaseGivexRequest<GetBalanceResponse> {

	public GetBalanceRequest(BasicRequestData data, String baseUrl, int timeoutMillis, Response.Listener<GetBalanceResponse> listener, Response.ErrorListener errorListener) {
		super(Method.POST, data, baseUrl, timeoutMillis, listener, errorListener);
	}

	@Override
	protected GetBalanceResponse responseInstance() {
		return new GetBalanceResponse();
	}
}
