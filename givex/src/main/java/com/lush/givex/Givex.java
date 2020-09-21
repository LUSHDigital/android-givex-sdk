package com.lush.givex;

import com.android.volley.Response;
import com.lush.givex.model.response.ActivateCardResponse;
import com.lush.givex.model.response.CancelTransactionResponse;
import com.lush.givex.model.response.CashBackResponse;
import com.lush.givex.model.response.GetBalanceResponse;
import com.lush.givex.model.response.RedemptionResponse;
import com.lush.givex.model.response.TopUpCardResponse;

/**
 * The top-level interface for all actions to be done against the Givex service.
 */
public interface Givex {

    void activateCard(String cardNumber, double amount, Response.Listener<ActivateCardResponse> listener, Response.ErrorListener errorListener);

    void cancelTransaction(String cardNumber, double amount, String authCode, Response.Listener<CancelTransactionResponse> listener, Response.ErrorListener errorListener);

    /**
     * Sends a request to obtain the balance on the Givex card with the input card number and immediately returns the transaction code used for that request.
     */
    String getBalance(String cardNumber, Response.Listener<GetBalanceResponse> listener, Response.ErrorListener errorListener);

    void topUp(String cardNumber, double amount, Response.Listener<TopUpCardResponse> listener, Response.ErrorListener errorListener);

    void redeem(String cardNumber, double amount, Response.Listener<RedemptionResponse> listener, Response.ErrorListener errorListener);

    void cashBack(String cardNumber, double amount, Response.Listener<CashBackResponse> listener, Response.ErrorListener errorListener);
}
