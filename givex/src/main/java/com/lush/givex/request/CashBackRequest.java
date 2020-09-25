package com.lush.givex.request;

import com.android.volley.Response;
import com.lush.givex.base.BaseGivexRequest;
import com.lush.givex.model.request.BasicRequestData;
import com.lush.givex.model.response.CashBackResponse;

/**
 * @author Matt Allen
 */
public final class CashBackRequest extends BaseGivexRequest<CashBackResponse> {

	public CashBackRequest(BasicRequestData data, String baseUrl, int timeoutMillis, Response.Listener<CashBackResponse> listener, Response.ErrorListener errorListener) {
		super(Method.POST, data, baseUrl, timeoutMillis, listener, errorListener);
	}

	@Override
	protected CashBackResponse responseInstance() {
		return new CashBackResponse();
	}
}
