package com.lush.givex.request;

import com.android.volley.Response;
import com.lush.givex.base.BaseGivexRequest;
import com.lush.givex.model.request.BasicRequestData;
import com.lush.givex.model.response.ActivateCardResponse;

/**
 * @author Matt Allen
 */
public final class ActivateCardRequest extends BaseGivexRequest<ActivateCardResponse> {

	public ActivateCardRequest(String baseUrl, BasicRequestData data, int timeoutMillis, Response.Listener<ActivateCardResponse> listener, Response.ErrorListener errorListener) {
		super(Method.POST, data, baseUrl, timeoutMillis, listener, errorListener);
	}

	@Override
	protected ActivateCardResponse responseInstance(String json) {
		return new ActivateCardResponse(json);
	}
}
