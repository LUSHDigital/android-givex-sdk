package com.lush.givex;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.lush.givex.fallback.GivexBackedUpRequestHandler;
import com.lush.givex.fallback.GivexRequestFactory;
import com.lush.givex.fallback.impl.GivexBackedUpRequestHandlerImpl;
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
import com.lush.givex.request.factories.ActivateCardRequestFactory;
import com.lush.givex.request.factories.CancelTransactionRequestFactory;
import com.lush.givex.request.factories.CashBackRequestFactory;
import com.lush.givex.request.factories.GetBalanceRequestFactory;
import com.lush.givex.request.factories.RedemptionRequestFactory;
import com.lush.givex.request.factories.TopUpCardRequestFactory;

final class VolleyGivex implements Givex {
	private final RequestQueue queue;
	private final int timeoutMillis;
	private final String username, password, languageCode, primaryUrl, secondaryUrl;

	private final GivexRequestFactory<ActivateCardResponse> activateCardRequestFactory = new ActivateCardRequestFactory();
	private final GivexRequestFactory<CancelTransactionResponse> cancelTransactionRequestFactory = new CancelTransactionRequestFactory();
	private final GivexRequestFactory<CashBackResponse> cashBackRequestFactory = new CashBackRequestFactory();
	private final GivexRequestFactory<GetBalanceResponse> getBalanceRequestFactory = new GetBalanceRequestFactory();
	private final GivexRequestFactory<RedemptionResponse> redemptionRequestFactory = new RedemptionRequestFactory();
	private final GivexRequestFactory<TopUpCardResponse> topUpCardRequestFactory = new TopUpCardRequestFactory();

	VolleyGivex(RequestQueue queue, GivexConfig config) {
		this.queue = queue;
		this.username = config.username;
		this.password = config.password;
		this.languageCode = config.languageCode;
		this.primaryUrl = config.baseUrl;
		this.secondaryUrl = config.fallbackUrl;
		this.timeoutMillis = config.timeoutMillis;
	}

	@Override
	public void activateCard(String cardNumber, double amount, Response.Listener<ActivateCardResponse> listener, Response.ErrorListener errorListener) {
		activateCard(cardNumber, amount, createTransactionCode(), "", listener, errorListener);
	}

	// This method is not private for test purposes only.
	void activateCard(String cardNumber, double amount, String transactionCode, String securityCode, Response.Listener<ActivateCardResponse> listener, Response.ErrorListener errorListener) {
		final BasicRequestData data = new ActivateCardRequestData(username, password, languageCode, transactionCode, amount, cardNumber, securityCode);
		final GivexBackedUpRequestHandler<ActivateCardResponse> requestHandler = new GivexBackedUpRequestHandlerImpl<ActivateCardResponse>(queue, timeoutMillis, primaryUrl, secondaryUrl);
		requestHandler.send(data, activateCardRequestFactory, listener, errorListener);
	}

	@Override
	public void cancelTransaction(String cardNumber, double amount, String authCode, Response.Listener<CancelTransactionResponse> listener, Response.ErrorListener errorListener) {
		cancelTransaction(createTransactionCode(), cardNumber, amount, authCode, "", listener, errorListener);
	}

	private void cancelTransaction(String transactionCode, String cardNumber, double amount, String givexAuthCode, String securityCode, Response.Listener<CancelTransactionResponse> listener, Response.ErrorListener errorListener) {
		final BasicRequestData data = new CancelTransactionRequestData(username, password, languageCode, transactionCode, cardNumber, amount, givexAuthCode, securityCode);
		final GivexBackedUpRequestHandler<CancelTransactionResponse> requestHandler = new GivexBackedUpRequestHandlerImpl<CancelTransactionResponse>(queue, timeoutMillis, primaryUrl, secondaryUrl);
		requestHandler.send(data, cancelTransactionRequestFactory, listener, errorListener);
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
		final GivexBackedUpRequestHandler<GetBalanceResponse> requestHandler = new GivexBackedUpRequestHandlerImpl<GetBalanceResponse>(queue, timeoutMillis, primaryUrl, secondaryUrl);
		requestHandler.send(data, getBalanceRequestFactory, listener, errorListener);
	}

	@Override
	public void topUp(String cardNumber, double amount, Response.Listener<TopUpCardResponse> listener, Response.ErrorListener errorListener) {
		topUp(cardNumber, amount, createTransactionCode(), "", listener, errorListener);
	}

	// This method is not private for test purposes only.
	void topUp(String cardNumber, double amount, String transactionCode, String securityCode, Response.Listener<TopUpCardResponse> listener, Response.ErrorListener errorListener) {
		final BasicRequestData data = new TopUpCardRequestData(username, password, languageCode, transactionCode, cardNumber, amount, securityCode);
		final GivexBackedUpRequestHandler<TopUpCardResponse> requestHandler = new GivexBackedUpRequestHandlerImpl<TopUpCardResponse>(queue, timeoutMillis, primaryUrl, secondaryUrl);
		requestHandler.send(data, topUpCardRequestFactory, listener, errorListener);
	}

	@Override
	public void redeem(String cardNumber, double amount, Response.Listener<RedemptionResponse> listener, Response.ErrorListener errorListener) {
		redeem(cardNumber, amount, createTransactionCode(), "", listener, errorListener);
	}

	// This method is not private for test purposes only.
	void redeem(String cardNumber, double amount, String transactionCode, String securityCode, Response.Listener<RedemptionResponse> listener, Response.ErrorListener errorListener) {
		final BasicRequestData data = new RedemptionRequestData(username, password, languageCode, transactionCode, cardNumber, amount, securityCode);
		final GivexBackedUpRequestHandler<RedemptionResponse> requestHandler = new GivexBackedUpRequestHandlerImpl<RedemptionResponse>(queue, timeoutMillis, primaryUrl, secondaryUrl);
		requestHandler.send(data, redemptionRequestFactory, listener, errorListener);
	}

	@Override
	public void cashBack(String cardNumber, double amount, Response.Listener<CashBackResponse> listener, Response.ErrorListener errorListener) {
		cashBack(cardNumber, amount, createTransactionCode(), "", listener, errorListener);
	}

	private void cashBack(String cardNumber, double amount, String transactionCode, String securityCode, Response.Listener<CashBackResponse> listener, Response.ErrorListener errorListener) {
		final BasicRequestData data = new CashBackRequestData(username, password, languageCode, transactionCode, cardNumber, amount, securityCode);
		final GivexBackedUpRequestHandler<CashBackResponse> requestHandler = new GivexBackedUpRequestHandlerImpl<CashBackResponse>(queue, timeoutMillis, primaryUrl, secondaryUrl);
		requestHandler.send(data, cashBackRequestFactory, listener, errorListener);
	}

	/**
	 * Requests require a transaction code to be a part of the request.
	 */
	private String createTransactionCode() {
		return String.valueOf(System.currentTimeMillis());
	}
}
