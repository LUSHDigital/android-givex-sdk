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

        return postActivateCardRequest(data);
    }

    public GetBalanceResponse getBalance(String cardNumber) throws IOException  {
        final BasicRequestData data = new GetBalanceRequestData(username, languageCode, transactionCode(), cardNumber, "");

        return postGetBalanceRequest(data);
    }

    public Pair<TopUpCardResponse, ReversalResponse> topUpAndReverse(String cardNumber, double topUpAmount) throws IOException  {
        final BasicRequestData topUpData = new TopUpCardRequestData(username, password, languageCode, transactionCode(), cardNumber, topUpAmount, "");
        final TopUpCardResponse topUpResponse = postTopUpRequest(topUpData);

        if (topUpResponse.isSuccess()) {
            final BasicRequestData reversalData = topUpData.getReversalData();
            final ReversalResponse reversalResponse = postReversalRequest(reversalData);

            return new Pair<>(topUpResponse, reversalResponse);
        } else {
            return new Pair<>(topUpResponse, null);
        }
    }

    public Pair<TopUpCardResponse, RedemptionResponse> topUpAndRedeem(String cardNumber, double redemptionAmount) throws IOException {
        final BasicRequestData topUpData = new TopUpCardRequestData(username, password, languageCode, transactionCode(), cardNumber, redemptionAmount, "");
        final TopUpCardResponse topUpResponse = postTopUpRequest(topUpData);

        if (topUpResponse.isSuccess()) {
            final BasicRequestData redemptionData = new RedemptionRequestData(username, password, languageCode, transactionCode(), cardNumber, redemptionAmount, "");
            final RedemptionResponse redemptionResponse = postRedemptionRequest(redemptionData);

            return new Pair<>(topUpResponse, redemptionResponse);
        } else {
            return new Pair<>(topUpResponse, null);
        }
    }

    public Pair<TopUpCardResponse, CancelTransactionResponse> topUpAndCancel(String cardNumber, double topUpAmount) throws IOException {
        final String transactionCode = transactionCode();

        final BasicRequestData topUpData = new TopUpCardRequestData(username, password, languageCode, transactionCode, cardNumber, topUpAmount, "");
        final TopUpCardResponse topUpResponse = postTopUpRequest(topUpData);

        if (topUpResponse.isSuccess()) {
            final BasicRequestData cancellationData = new CancelTransactionRequestData(username, password, languageCode, transactionCode, cardNumber, topUpAmount, "", "");
            final CancelTransactionResponse cancelTransactionResponse = postCancelTransactionRequest(cancellationData);

            return new Pair<>(topUpResponse, cancelTransactionResponse);
        } else {
            return new Pair<>(topUpResponse, null);
        }
    }

    public Pair<TopUpCardResponse, CashBackResponse> topUpAndCashBack(String cardNumber, double cashBackAmount) throws IOException  {
        final BasicRequestData topUpData = new TopUpCardRequestData(username, password, languageCode, transactionCode(), cardNumber, cashBackAmount, "");
        final TopUpCardResponse topUpResponse = postTopUpRequest(topUpData);

        if (topUpResponse.isSuccess()) {
            final BasicRequestData cashBackData = new CashBackRequestData(username, password, languageCode, transactionCode(), cardNumber, cashBackAmount, "");
            final CashBackResponse cashBackResponse = postCashBackRequest(cashBackData);

            return new Pair<>(topUpResponse, cashBackResponse);
        } else {
            return new Pair<>(topUpResponse, null);
        }
    }

    private ActivateCardResponse postActivateCardRequest(BasicRequestData requestData) throws IOException {
        final String responseJson = httpClient.post("", requestData.getRequestBody());
        return new ActivateCardResponse(responseJson);
    }

    private CancelTransactionResponse postCancelTransactionRequest(BasicRequestData requestData) throws IOException {
        final String responseJson = httpClient.post("", requestData.getRequestBody());
        return new CancelTransactionResponse(responseJson);
    }

    private CashBackResponse postCashBackRequest(BasicRequestData requestData) throws IOException {
        final String responseJson = httpClient.post("", requestData.getRequestBody());
        return new CashBackResponse(responseJson);
    }

    private GetBalanceResponse postGetBalanceRequest(BasicRequestData requestData) throws IOException {
        final String responseJson = httpClient.post("", requestData.getRequestBody());
        return new GetBalanceResponse(responseJson);
    }

    private RedemptionResponse postRedemptionRequest(BasicRequestData requestData) throws IOException {
        final String responseJson = httpClient.post("", requestData.getRequestBody());
        return new RedemptionResponse(responseJson);
    }

    private ReversalResponse postReversalRequest(BasicRequestData requestData) throws IOException {
        final String responseJson = httpClient.post("", requestData.getRequestBody());
        return new ReversalResponse(responseJson);
    }

    private TopUpCardResponse postTopUpRequest(BasicRequestData requestData) throws IOException {
        final String responseJson = httpClient.post("", requestData.getRequestBody());
        return new TopUpCardResponse(responseJson);
    }

    private String transactionCode() {
        return Long.toString(System.currentTimeMillis());
    }
}
