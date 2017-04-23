package com.lush.givex;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * The top-level interface for all actions to be done against the Givex service
 *
 * @author Matt Allen
 */
public class Givex
{
	private RequestQueue queue;
	private String username, password, languageCode;
	private Environment environment;

	public Givex(Context context, String username, String password, String languageCode)
	{
		this(context, username, password, Environment.PROD, languageCode);
	}

	public Givex(Context context, String username, String password, Environment environment, String languageCode)
	{
		this.queue = Volley.newRequestQueue(context);
		this.username = username;
		this.password = password;
		this.environment = environment;
		this.languageCode = languageCode;
	}

	public void activateCard(String cardNumber)
	{

	}
}
