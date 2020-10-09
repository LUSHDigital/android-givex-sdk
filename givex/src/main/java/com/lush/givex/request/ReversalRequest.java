package com.lush.givex.request;

import com.android.volley.Response;
import com.lush.givex.base.BaseGivexRequest;
import com.lush.givex.model.request.BasicRequestData;
import com.lush.givex.model.response.ReversalResponse;

public final class ReversalRequest extends BaseGivexRequest<ReversalResponse> {

	public ReversalRequest(BasicRequestData data, String baseUrl, Response.Listener<ReversalResponse> listener, Response.ErrorListener errorListener) {
		super(Method.POST, data, baseUrl, listener, errorListener);
	}

	@Override
	protected ReversalResponse responseInstance(String json) {
		return new ReversalResponse(json);
	}
}
