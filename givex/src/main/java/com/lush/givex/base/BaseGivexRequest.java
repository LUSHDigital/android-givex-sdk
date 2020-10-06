package com.lush.givex.base;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;

import com.android.volley.VolleyError;
import com.lush.givex.model.request.BasicRequestData;
import com.lush.givex.model.response.GivexResponse;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Matt Allen
 */
public abstract class BaseGivexRequest<T extends GivexResponse> extends Request<T> {
	private static final int GIVEX_PORT = 50104;

	private final Response.Listener<T> listener;
	private final BasicRequestData data;
	private final Map<String, String> headers;

	public BaseGivexRequest(int method, BasicRequestData data, String baseUrl, Response.Listener<T> listener, Response.ErrorListener errorListener) {
		this(method, data, baseUrl, 0, listener, errorListener);
	}

	public BaseGivexRequest(int method, BasicRequestData data, String baseUrl, int timeoutMillis, Response.Listener<T> listener, Response.ErrorListener errorListener) {
		super(method, (baseUrl + ':' + GIVEX_PORT), errorListener);

		this.listener = listener;
		this.data = data;
		this.headers = buildHeaders();

		if (timeoutMillis > 0) {
			setRetryPolicy(new DefaultRetryPolicy(timeoutMillis, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		}
	}

	private Map<String, String> buildHeaders() {
		final Map<String, String> headers = new HashMap<>(1);
		headers.put("Content-Type", "application/json");

		return headers;
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		return headers;
	}

	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		final String json = new String(response.data).trim();

		if (json.length() > 0) {
			Log.d(getClass().getSimpleName(), json);

			return parseJsonResponse(json);
		} else {
			return Response.error(new VolleyError(response));
		}
	}

	private Response<T> parseJsonResponse(String json) {
		try {
			final T responseObj = responseInstance(json);

			return Response.success(responseObj, getCacheEntry());
		} catch (Exception e) {
			Log.e(getClass().getSimpleName(), "Error in parsing the Givex JSON response.", e);
			return Response.error(new VolleyError("Error in parsing the Givex JSON response.", e));
		}
	}

	@Override
	protected void deliverResponse(T response) {
		if (listener != null) {
			listener.onResponse(response);
		}
	}

	@Override
	public byte[] getBody() throws AuthFailureError {
		if (hasBody()) {
			final String body = data.getRequestBody();
			Log.d(getClass().getSimpleName(), body);

			try {
				return body.getBytes("UTF-8");
			} catch (UnsupportedEncodingException e) {
				return body.getBytes();
			}
		} else {
			return null;
		}
	}

	private boolean hasBody() {
		final int method = getMethod();

		return (method == Method.POST || method == Method.PUT || method == Method.PATCH);
	}

	@Override
	public Object getTag() {
		return "givex";
	}

	protected abstract T responseInstance(String json);
}
