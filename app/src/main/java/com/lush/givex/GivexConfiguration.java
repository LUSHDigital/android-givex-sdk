package com.lush.givex;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * @author Matt Allen
 */
public class GivexConfiguration
{
	private static GivexConfiguration instance;

	public static GivexConfiguration getInstance()
	{
		if (instance == null)
		{
			synchronized (GivexConfiguration.class)
			{
				if (instance == null)
				{
					instance = new GivexConfiguration();
				}
			}
		}
		return instance;
	}

	private RequestQueue queue;
	private Environment environment = Environment.PROD;

	public void createRequestQueue(Context context)
	{
		queue = Volley.newRequestQueue(context);
	}

	public Environment getEnvironment()
	{
		return environment;
	}

	public void setEnvironment(Environment environment)
	{
		this.environment = environment;
	}
}
