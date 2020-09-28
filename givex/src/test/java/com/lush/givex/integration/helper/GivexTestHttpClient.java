package com.lush.givex.integration.helper;

import com.google.gson.Gson;
import com.lush.givex.model.request.BasicRequestData;
import com.lush.givex.model.request.GetBalanceRequestData;
import com.lush.givex.model.request.TopUpCardRequestData;

import java.io.IOException;
import java.util.List;

public final class GivexTestHttpClient {
    private static final int BALANCE_AMOUNT_INDEX = 2;
    private static final double ERROR_BALANCE_AMOUNT = -1.0;

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

    public double getBalance(String cardNumber) throws IOException  {
        final BasicRequestData data = new GetBalanceRequestData(username, languageCode, transactionCode(), cardNumber, "");

        final String getBalanceJson = httpClient.post("", data.getRequestBody());
        final GivexResponse getBalanceResponse = gson.fromJson(getBalanceJson, GivexResponse.class);
        if (getBalanceResponse.resultIsOk()) {
            final List<String> result = getBalanceResponse.getResult();
            if (result.size() > BALANCE_AMOUNT_INDEX) {
                try {
                    return Double.parseDouble(result.get(BALANCE_AMOUNT_INDEX));
                } catch (Exception e) {
                    System.err.println("Unexpected balance value in get-balance result: " + getBalanceJson);
                    return ERROR_BALANCE_AMOUNT;
                }
            } else {
                System.err.println("Unexpected get-balance result: " + getBalanceJson);
                return ERROR_BALANCE_AMOUNT;
            }
        } else {
            System.err.println("Get-balance error: " + getBalanceJson);
            return ERROR_BALANCE_AMOUNT;
        }
    }

    public boolean topUpAndReverse(String cardNumber, double topUpAmount) throws IOException  {
        final BasicRequestData data = new TopUpCardRequestData(username, password, languageCode, transactionCode(), cardNumber, topUpAmount, "");

        final String topUpJson = httpClient.post("", data.getRequestBody());
        final GivexResponse topUpResponse = gson.fromJson(topUpJson, GivexResponse.class);
        if (topUpResponse.resultIsOk()) {
            final String reversalJson = httpClient.post("", data.getReversalData().getRequestBody());
            final GivexResponse reversalResponse = gson.fromJson(reversalJson, GivexResponse.class);
            if (reversalResponse.resultIsOk()) {
                return true;
            } else {
                System.err.println("Top-up reversal error: " + reversalJson);
                return false;
            }
        } else {
            System.err.println("Top-up error: " + topUpJson);
            return false;
        }
    }

    private String transactionCode() {
        return Long.toString(System.currentTimeMillis());
    }
}
