package com.lush.givex;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.lush.givex.model.request.ActivateCardRequestData;
import com.lush.givex.model.request.CancelTransactionRequestData;
import com.lush.givex.model.request.GetBalanceRequestData;
import com.lush.givex.model.request.RedemptionRequestData;
import com.lush.givex.model.request.TopUpCardRequestData;
import com.lush.givex.model.response.ActivateCardResponse;
import com.lush.givex.model.response.CancelTransactionResponse;
import com.lush.givex.model.response.GetBalanceResponse;
import com.lush.givex.model.response.RedemptionResponse;
import com.lush.givex.model.response.TopUpCardResponse;
import com.lush.givex.request.ActivateCardRequest;
import com.lush.givex.request.CancelTransactionRequest;
import com.lush.givex.request.GetBalanceRequest;
import com.lush.givex.request.RedemptionRequest;
import com.lush.givex.request.TopUpCardRequest;

/**
 * The top-level interface for all actions to be done against the Givex service.
 *
 * @author Matt Allen
 */
public class Givex
{
	private RequestQueue queue;
	private String username, password, languageCode, baseUrl;

	public Givex(Context context, String username, String password, String languageCode)
	{
		this(context, username, password, Environment.PROD, languageCode);
	}

	public Givex(Context context, String username, String password, String baseUrl, String languageCode)
	{
		this.queue = Volley.newRequestQueue(context);
		this.username = username;
		this.password = password;
		this.baseUrl = baseUrl;
		this.languageCode = languageCode;
	}

	/**
	 * Requests require a transaction code to be a part of the request.
	 *
	 * @return Generated transaction code based on environment variables.
	 */
	public String createTransactionCode()
	{
		return String.valueOf(System.currentTimeMillis());
	}

	public void activateCard(String cardNumber, double amount, Response.Listener<ActivateCardResponse> listener, Response.ErrorListener errorListener)
	{
		activateCard(cardNumber, amount, createTransactionCode(), "", listener, errorListener);
	}

	public void activateCard(String cardNumber, double amount, String transactionCode, String securityCode, Response.Listener<ActivateCardResponse> listener, Response.ErrorListener errorListener)
	{
		ActivateCardRequestData data = new ActivateCardRequestData(username, password, languageCode, transactionCode, amount, cardNumber, securityCode);
		Request request = new ActivateCardRequest(baseUrl, Request.Method.POST, data, listener, errorListener);
		queue.add(request);
	}

	public void cancelTransaction(String cardNumber, double amount, String authCode, Response.Listener<CancelTransactionResponse> listener, Response.ErrorListener errorListener)
	{
		cancelTransaction(createTransactionCode(), cardNumber, amount, authCode, "", listener, errorListener);
	}

	public void cancelTransaction(String transactionCode, String cardNumber, double amount, String givexAuthCode, String securityCode, Response.Listener<CancelTransactionResponse> listener, Response.ErrorListener errorListener)
	{
		CancelTransactionRequestData data = new CancelTransactionRequestData(username, password, languageCode, transactionCode, cardNumber, amount, givexAuthCode, securityCode);
		CancelTransactionRequest request = new CancelTransactionRequest(data, baseUrl, listener, errorListener);
		queue.add(request);
	}

	public void getBalance(String cardNumber, Response.Listener<GetBalanceResponse> listener, Response.ErrorListener errorListener)
	{
		getBalance(cardNumber, createTransactionCode(), "", listener, errorListener);
	}

	public void getBalance(String cardNumber, String transactionCode, String securityCode, Response.Listener<GetBalanceResponse> listener, Response.ErrorListener errorListener)
	{
		GetBalanceRequestData data = new GetBalanceRequestData(username, languageCode, transactionCode, cardNumber, securityCode);
		GetBalanceRequest request = new GetBalanceRequest(data, baseUrl, listener, errorListener);
		queue.add(request);
	}

	public void topUp(String cardNumber, double amount, Response.Listener<TopUpCardResponse> listener, Response.ErrorListener errorListener)
	{
		topUp(cardNumber, amount, createTransactionCode(), "", listener, errorListener);
	}

	public void topUp(String cardNumber, double amount, String transactionCode, String securityCode, Response.Listener<TopUpCardResponse> listener, Response.ErrorListener errorListener)
	{
		TopUpCardRequestData data = new TopUpCardRequestData(username, password, languageCode, transactionCode, cardNumber, amount, securityCode);
		TopUpCardRequest request = new TopUpCardRequest(data, baseUrl, listener, errorListener);
		queue.add(request);
	}

	public void redeem(String cardNumber, double amount, Response.Listener<RedemptionResponse> listener, Response.ErrorListener errorListener)
	{
		redeem(cardNumber, amount, createTransactionCode(), "", listener, errorListener);
	}

	public void redeem(String cardNumber, double amount, String transactionCode, String securityCode, Response.Listener<RedemptionResponse> listener, Response.ErrorListener errorListener)
	{
		RedemptionRequestData data = new RedemptionRequestData(username, password, languageCode, transactionCode, cardNumber, amount, securityCode);
		RedemptionRequest request = new RedemptionRequest(data, baseUrl, listener, errorListener);
		queue.add(request);
	}
}
