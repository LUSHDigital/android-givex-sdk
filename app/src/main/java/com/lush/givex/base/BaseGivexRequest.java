package com.lush.givex.base;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;

import com.lush.givex.Environment;
import com.lush.givex.GivexConfiguration;

/**
 * @author Matt Allen
 */
public class BaseGivexRequest<T> extends Request<T>
{
	private static final String BASE_URL = "https://dev-dataconnect.givex.com";

	private Response.Listener<T> listener;
	private Environment environment = Environment.PROD;
	private String endpoint;

	public BaseGivexRequest(int method, String endpoint, Response.Listener<T> listener, Response.ErrorListener errorListener)
	{
		super(method, null, errorListener);
		setRetryPolicy(new DefaultRetryPolicy(5000, 2, 1.5f));
		this.listener = listener;
		this.endpoint = endpoint;
		environment = GivexConfiguration.getInstance().getEnvironment();
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
	protected Response<T> parseNetworkResponse(NetworkResponse response)
	{
		return null;
	}

	@Override
	protected void deliverResponse(T response)
	{

	}
}
