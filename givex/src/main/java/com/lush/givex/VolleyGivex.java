package com.lush.givex;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.lush.givex.model.request.ActivateCardRequestData;
import com.lush.givex.model.request.BasicRequestData;
import com.lush.givex.model.request.CancelTransactionRequestData;
import com.lush.givex.model.request.CashBackRequestData;
import com.lush.givex.model.request.GetBalanceRequestData;
import com.lush.givex.model.request.RedemptionRequestData;
import com.lush.givex.model.request.TopUpCardRequestData;
import com.lush.givex.model.response.ActivateCardResponse;
import com.lush.givex.model.response.CancelTransactionResponse;
import com.lush.givex.model.response.CashBackResponse;
import com.lush.givex.model.response.GetBalanceResponse;
import com.lush.givex.model.response.RedemptionResponse;
import com.lush.givex.model.response.TopUpCardResponse;
import com.lush.givex.request.ActivateCardRequest;
import com.lush.givex.request.CancelTransactionRequest;
import com.lush.givex.request.CashBackRequest;
import com.lush.givex.request.GetBalanceRequest;
import com.lush.givex.request.RedemptionRequest;
import com.lush.givex.request.TopUpCardRequest;

final class VolleyGivex implements Givex {
	private final RequestQueue queue;
	private final int timeoutMillis;
	private final String username, password, languageCode, baseUrl, fallbackUrl;

	VolleyGivex(RequestQueue queue, GivexConfig config) {
		this.queue = queue;
		this.username = config.username;
		this.password = config.password;
		this.languageCode = config.languageCode;
		this.baseUrl = config.baseUrl;
		this.fallbackUrl = config.fallbackUrl;
		this.timeoutMillis = config.timeoutMillis;
	}

	@Override
	public void activateCard(String cardNumber, double amount, Response.Listener<ActivateCardResponse> listener, Response.ErrorListener errorListener) {
		activateCard(cardNumber, amount, createTransactionCode(), "", listener, errorListener);
	}

	// This method is not private for test purposes only.
	void activateCard(String cardNumber, double amount, String transactionCode, String securityCode, Response.Listener<ActivateCardResponse> listener, Response.ErrorListener errorListener) {
		final BasicRequestData data = new ActivateCardRequestData(username, password, languageCode, transactionCode, amount, cardNumber, securityCode);
		final Request<ActivateCardResponse> request = new ActivateCardRequest(baseUrl, Request.Method.POST, data, timeoutMillis, listener, errorListener);
		queue.add(request);
	}

	@Override
	public void cancelTransaction(String cardNumber, double amount, String authCode, Response.Listener<CancelTransactionResponse> listener, Response.ErrorListener errorListener) {
		cancelTransaction(createTransactionCode(), cardNumber, amount, authCode, "", listener, errorListener);
	}

	private void cancelTransaction(String transactionCode, String cardNumber, double amount, String givexAuthCode, String securityCode, Response.Listener<CancelTransactionResponse> listener, Response.ErrorListener errorListener) {
		final BasicRequestData data = new CancelTransactionRequestData(username, password, languageCode, transactionCode, cardNumber, amount, givexAuthCode, securityCode);
		final Request<CancelTransactionResponse> request = new CancelTransactionRequest(data, baseUrl, timeoutMillis, listener, errorListener);
		queue.add(request);
	}

	@Override
	public String getBalance(String cardNumber, Response.Listener<GetBalanceResponse> listener, Response.ErrorListener errorListener) {
		final String transactionCode = createTransactionCode();
		getBalance(cardNumber, transactionCode, "", listener, errorListener);

		return transactionCode;
	}

	// This method is not private for test purposes only.
	void getBalance(String cardNumber, String transactionCode, String securityCode, Response.Listener<GetBalanceResponse> listener, Response.ErrorListener errorListener) {
		final BasicRequestData data = new GetBalanceRequestData(username, languageCode, transactionCode, cardNumber, securityCode);
		final Request<GetBalanceResponse> request = new GetBalanceRequest(data, baseUrl, timeoutMillis, listener, errorListener);
		queue.add(request);
	}

	@Override
	public void topUp(String cardNumber, double amount, Response.Listener<TopUpCardResponse> listener, Response.ErrorListener errorListener) {
		topUp(cardNumber, amount, createTransactionCode(), "", listener, errorListener);
	}

	// This method is not private for test purposes only.
	void topUp(String cardNumber, double amount, String transactionCode, String securityCode, Response.Listener<TopUpCardResponse> listener, Response.ErrorListener errorListener) {
		final BasicRequestData data = new TopUpCardRequestData(username, password, languageCode, transactionCode, cardNumber, amount, securityCode);
		final Request<TopUpCardResponse> request = new TopUpCardRequest(data, baseUrl, timeoutMillis, listener, errorListener);
		queue.add(request);
	}

	@Override
	public void redeem(String cardNumber, double amount, Response.Listener<RedemptionResponse> listener, Response.ErrorListener errorListener) {
		redeem(cardNumber, amount, createTransactionCode(), "", listener, errorListener);
	}

	// This method is not private for test purposes only.
	void redeem(String cardNumber, double amount, String transactionCode, String securityCode, Response.Listener<RedemptionResponse> listener, Response.ErrorListener errorListener) {
		final BasicRequestData data = new RedemptionRequestData(username, password, languageCode, transactionCode, cardNumber, amount, securityCode);
		final Request<RedemptionResponse> request = new RedemptionRequest(data, baseUrl, timeoutMillis, listener, errorListener);
		queue.add(request);
	}

	@Override
	public void cashBack(String cardNumber, double amount, Response.Listener<CashBackResponse> listener, Response.ErrorListener errorListener) {
		cashBack(cardNumber, amount, createTransactionCode(), "", listener, errorListener);
	}

	private void cashBack(String cardNumber, double amount, String transactionCode, String securityCode, Response.Listener<CashBackResponse> listener, Response.ErrorListener errorListener) {
		final BasicRequestData data = new CashBackRequestData(username, password, languageCode, transactionCode, cardNumber, amount, securityCode);
		final Request<CashBackResponse> request = new CashBackRequest(data, baseUrl, timeoutMillis, listener, errorListener);
		queue.add(request);
	}

	/**
	 * Requests require a transaction code to be a part of the request.
	 */
	private String createTransactionCode() {
		return String.valueOf(System.currentTimeMillis());
	}
}
