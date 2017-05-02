package com.lush.givex.base;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;

import com.android.volley.VolleyError;
import com.lush.givex.Environment;
import com.lush.givex.model.request.BasicRequestData;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Matt Allen
 */
public abstract class BaseGivexRequest<T> extends Request<T>
{
	private static final String BASE_URL = "https://dev-dataconnect.givex.com";

	private Response.Listener<T> listener;
	private Environment environment = Environment.PROD;
	private BasicRequestData data;

	public BaseGivexRequest(int method, BasicRequestData data, Environment environment, Response.Listener<T> listener, Response.ErrorListener errorListener)
	{
		super(method, null, errorListener);
		setRetryPolicy(new DefaultRetryPolicy(5000, 2, 1.5f));
		this.listener = listener;
		this.environment = environment;
		this.data = data;
	}

	@Override
	public String getUrl()
	{
		if (environment == Environment.TEST)
		{
			return BASE_URL + ":50104";
		}
		return BASE_URL;
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError
	{
		Map<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");
		return headers;
	}

	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response)
	{
		String json = new String(response.data);
		if (json.trim().length() > 0)
		{
			T responseObj;
			try
			{
				responseObj = createResponse(json);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return Response.error(new VolleyError(e.getMessage()));
			}

			if (responseObj != null)
			{
				return Response.success(responseObj, getCacheEntry());
			}
		}
		return Response.error(new VolleyError(response));
	}

	@Override
	protected void deliverResponse(T response)
	{
		if (listener != null)
		{
			listener.onResponse(response);
		}
	}

	@Override
	public byte[] getBody() throws AuthFailureError
	{
		if (getMethod() == Method.POST)
		{
			String body = data.getRequestBody();
			try
			{
				return body.getBytes("UTF-8");
			}
			catch (UnsupportedEncodingException e)
			{
				return body.getBytes();
			}

		}
		else return null;
	}

	protected abstract T createResponse(String networkResponse);
}
