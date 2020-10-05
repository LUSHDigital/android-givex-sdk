package com.lush.givex.integration.helper;

import com.google.gson.Gson;
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
import com.lush.givex.model.response.GivexResponse;
import com.lush.givex.model.response.RedemptionResponse;
import com.lush.givex.model.response.ReversalResponse;
import com.lush.givex.model.response.TopUpCardResponse;

import java.io.IOException;

public final class GivexTestHttpClient {
    private final String username;
    private final String password;
    private final String languageCode;

    private final Gson gson = new Gson();
    private final TestHttpClient httpClient = new TestHttpClient("https://dev-dataconnect.givex.com:50104");

    public GivexTestHttpClient(String username, String password, String languageCode) {
        this.username = username;
        this.password = password;
        this.languageCode = languageCode;
    }

    public ActivateCardResponse activateCard(String cardNumber, double amount) throws IOException {
        final BasicRequestData data = new ActivateCardRequestData(username, password, languageCode, transactionCode(), amount, cardNumber, "");
        final ActivateCardResponse response = new ActivateCardResponse();
        postRequest(data, response);

        return response;
    }

    public GetBalanceResponse getBalance(String cardNumber) throws IOException  {
        final BasicRequestData data = new GetBalanceRequestData(username, languageCode, transactionCode(), cardNumber, "");
        final GetBalanceResponse response = new GetBalanceResponse();
        postRequest(data, response);

        return response;
    }

    public Pair<TopUpCardResponse, ReversalResponse> topUpAndReverse(String cardNumber, double topUpAmount) throws IOException  {
        final BasicRequestData topUpData = new TopUpCardRequestData(username, password, languageCode, transactionCode(), cardNumber, topUpAmount, "");
        final TopUpCardResponse topUpResponse = new TopUpCardResponse();

        final BasicRequestData reversalData = topUpData.getReversalData();
        final ReversalResponse reversalResponse = new ReversalResponse();

        postRequest(topUpData, topUpResponse);
        if (topUpResponse.isSuccess()) {
            postRequest(reversalData, reversalResponse);
        }

        return new Pair<>(topUpResponse, reversalResponse);
    }

    public Pair<TopUpCardResponse, RedemptionResponse> topUpAndRedeem(String cardNumber, double redemptionAmount) throws IOException {
        final BasicRequestData topUpData = new TopUpCardRequestData(username, password, languageCode, transactionCode(), cardNumber, redemptionAmount, "");
        final TopUpCardResponse topUpResponse = new TopUpCardResponse();

        final BasicRequestData redemptionData = new RedemptionRequestData(username, password, languageCode, transactionCode(), cardNumber, redemptionAmount, "");
        final RedemptionResponse redemptionResponse = new RedemptionResponse();

        postRequest(topUpData, topUpResponse);
        if (topUpResponse.isSuccess()) {
            postRequest(redemptionData, redemptionResponse);
        }

        return new Pair<>(topUpResponse, redemptionResponse);
    }

    public Pair<TopUpCardResponse, CancelTransactionResponse> topUpAndCancel(String cardNumber, double topUpAmount) throws IOException {
        final String transactionCode = transactionCode();

        final BasicRequestData topUpData = new TopUpCardRequestData(username, password, languageCode, transactionCode, cardNumber, topUpAmount, "");
        final TopUpCardResponse topUpResponse = new TopUpCardResponse();

        final BasicRequestData cancellationData = new CancelTransactionRequestData(username, password, languageCode, transactionCode, cardNumber, topUpAmount, "", "");
        final CancelTransactionResponse cancelTransactionResponse = new CancelTransactionResponse();

        postRequest(topUpData, topUpResponse);
        if (topUpResponse.isSuccess()) {
            postRequest(cancellationData, cancelTransactionResponse);
        }

        return new Pair<>(topUpResponse, cancelTransactionResponse);
    }

    public Pair<TopUpCardResponse, CashBackResponse> topUpAndCashBack(String cardNumber, double cashBackAmount) throws IOException  {
        final BasicRequestData topUpData = new TopUpCardRequestData(username, password, languageCode, transactionCode(), cardNumber, cashBackAmount, "");
        final TopUpCardResponse topUpResponse = new TopUpCardResponse();

        final BasicRequestData cashBackData = new CashBackRequestData(username, password, languageCode, transactionCode(), cardNumber, cashBackAmount, "");
        final CashBackResponse cashBackResponse = new CashBackResponse();

        postRequest(topUpData, topUpResponse);
        if (topUpResponse.isSuccess()) {
            postRequest(cashBackData, cashBackResponse);
        }

        return new Pair<>(topUpResponse, cashBackResponse);
    }

    private void postRequest(BasicRequestData requestData, GivexResponse responseHolder) throws IOException {
        final String responseJson = httpClient.post("", requestData.getRequestBody());
        responseHolder.fromNetworkResponse(responseJson);
    }

    private String transactionCode() {
        return Long.toString(System.currentTimeMillis());
    }
}
