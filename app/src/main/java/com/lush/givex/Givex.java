package com.lush.givex;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.lush.givex.model.request.ActivateCardRequestData;
import com.lush.givex.model.request.CancelCardRequestData;
import com.lush.givex.model.request.GetBalanceRequestData;
import com.lush.givex.model.request.RedemptionRequestData;
import com.lush.givex.model.request.TopUpCardRequestData;
import com.lush.givex.model.response.ActivateCardResponse;
import com.lush.givex.model.response.CancelCardResponse;
import com.lush.givex.model.response.GetBalanceResponse;
import com.lush.givex.model.response.RedemptionResponse;
import com.lush.givex.model.response.TopUpCardResponse;
import com.lush.givex.request.ActivateCardRequest;
import com.lush.givex.request.CancelCardRequest;
import com.lush.givex.request.GetBalanceRequest;
import com.lush.givex.request.RedemptionRequest;
import com.lush.givex.request.TopUpCardRequest;

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
		return String.valueOf(System.currentTimeMillis());
	}

	public void activateCard(String cardNumber, double amount, Response.Listener<ActivateCardResponse> listener, Response.ErrorListener errorListener)
	{
		activateCard(cardNumber, amount, createTransactionCode(), listener, errorListener);
	}

	public void activateCard(String cardNumber, double amount, String transactionCode, Response.Listener<ActivateCardResponse> listener, Response.ErrorListener errorListener)
	{
		ActivateCardRequestData data = new ActivateCardRequestData(username, password, languageCode, transactionCode, amount, cardNumber);
		Request request = new ActivateCardRequest(environment, Request.Method.POST, data, listener, errorListener);
		queue.add(request);
	}

	public void cancelCard(String cardNumber, double amount, Response.Listener<CancelCardResponse> listener, Response.ErrorListener errorListener)
	{
		cancelCard(createTransactionCode(), cardNumber, amount, listener, errorListener);
	}

	public void cancelCard(String transactionCode, String cardNumber, double amount, Response.Listener<CancelCardResponse> listener, Response.ErrorListener errorListener)
	{
		CancelCardRequestData data = new CancelCardRequestData(username, password, languageCode, transactionCode, cardNumber, amount);
		CancelCardRequest request = new CancelCardRequest(data, environment, listener, errorListener);
		queue.add(request);
	}

	public void getBalance(String cardNumber, Response.Listener<GetBalanceResponse> listener, Response.ErrorListener errorListener)
	{
		getBalance(cardNumber, createTransactionCode(), listener, errorListener);
	}

	public void getBalance(String cardNumber, String transactionCode, Response.Listener<GetBalanceResponse> listener, Response.ErrorListener errorListener)
	{
		GetBalanceRequestData data = new GetBalanceRequestData(username, languageCode, transactionCode, cardNumber);
		GetBalanceRequest request = new GetBalanceRequest(data, environment, listener, errorListener);
		queue.add(request);
	}

	public void topUp(String cardNumber, double amount, Response.Listener<TopUpCardResponse> listener, Response.ErrorListener errorListener)
	{
		topUp(cardNumber, amount, createTransactionCode(), listener, errorListener);
	}

	public void topUp(String cardNumber, double amount, String transactionCode, Response.Listener<TopUpCardResponse> listener, Response.ErrorListener errorListener)
	{
		TopUpCardRequestData data = new TopUpCardRequestData(username, password, languageCode, transactionCode, cardNumber, amount);
		TopUpCardRequest request = new TopUpCardRequest(data, environment, listener, errorListener);
		queue.add(request);
	}

	public void redeem(String cardNumber, double amount, Response.Listener<RedemptionResponse> listener, Response.ErrorListener errorListener)
	{
		redeem(cardNumber, amount, createTransactionCode(), listener, errorListener);
	}

	public void redeem(String cardNumber, double amount, String transactionCode, Response.Listener<RedemptionResponse> listener, Response.ErrorListener errorListener)
	{
		RedemptionRequestData data = new RedemptionRequestData(username, password, languageCode, transactionCode, cardNumber, amount);
		RedemptionRequest request = new RedemptionRequest(data, environment, listener, errorListener);
		queue.add(request);
	}
}
