package com.lush.givex;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.lush.givex.model.request.ActivateCardRequestData;
import com.lush.givex.model.response.ActivateCardResponse;
import com.lush.givex.request.ActivateCardRequest;

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

	/**
	 * Some requests require a transaction code to be a part of the request.
	 *
	 * @return Generated transaction code based on environment variables.
	 */
	public String createTransactionCode()
	{
		return languageCode + "-" + String.valueOf(System.currentTimeMillis());
	}

	public String activateCard(String cardNumber, double amount, Response.Listener<ActivateCardResponse> listener, Response.ErrorListener errorListener)
	{
		return activateCard(cardNumber, amount, createTransactionCode(), listener, errorListener);
	}

	public String activateCard(String cardNumber, double amount, String transactionCode, Response.Listener<ActivateCardResponse> listener, Response.ErrorListener errorListener)
	{
		ActivateCardRequestData data = new ActivateCardRequestData(username, password, languageCode, transactionCode, amount, cardNumber);
		Request request = new ActivateCardRequest(environment, Request.Method.POST, data, listener, errorListener);
		queue.add(request);
		return transactionCode;
	}
}
