package com.lush.givex.integration;

import com.lush.givex.integration.helper.GivexTestHttpClient;
import com.lush.givex.integration.helper.Pair;
import com.lush.givex.model.response.ActivateCardResponse;
import com.lush.givex.model.response.CancelTransactionResponse;
import com.lush.givex.model.response.CashBackResponse;
import com.lush.givex.model.response.GetBalanceResponse;
import com.lush.givex.model.response.RedemptionResponse;
import com.lush.givex.model.response.ReversalResponse;
import com.lush.givex.model.response.TopUpCardResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;
import java.util.Properties;

public final class GivexIntegrationTest {
    private final String cardNumber = "603628851842001640659";

    private GivexTestHttpClient test;

//    @Before
    public void setUp() throws Exception {
        final Properties props = new Properties();
        props.load(new FileReader("../local.properties"));

        final String username = props.getProperty("givex_test_username");
        final String password = props.getProperty("givex_test_password");
        test = new GivexTestHttpClient(username, password, "en");
    }

//    @Test
    public void shouldNotActivateAlreadyActiveCard() throws Exception {
        final ActivateCardResponse cardActivation = test.activateCard(cardNumber, 7.45);
        Assert.assertNotNull(cardActivation);
        Assert.assertFalse(cardActivation.isSuccess());
        Assert.assertFalse(cardActivation.doesNotExist());
        Assert.assertTrue(cardActivation.alreadyActive());
    }

//    @Test
    public void shouldGetBalance() throws Exception {
        final GetBalanceResponse balance = test.getBalance(cardNumber);
        Assert.assertNotNull(balance);
        Assert.assertTrue(balance.isSuccess());
        Assert.assertTrue(balance.getBalance() >= 0.0);
        Assert.assertNotNull(balance.getCurrencyCode());
        Assert.assertFalse(balance.hasExpired());
    }

//    @Test
    public void shouldTopUpAndReverse() throws Exception {
        final Pair<TopUpCardResponse, ReversalResponse> responsePair = test.topUpAndReverse(cardNumber, 14.75);
        Assert.assertNotNull(responsePair);

        final TopUpCardResponse topUp = responsePair.first;
        Assert.assertNotNull(topUp);
        Assert.assertTrue(topUp.isSuccess());
        Assert.assertNotNull(topUp.getTransactionReference());
        Assert.assertTrue(topUp.getNewBalance() >= 0.0);

        final ReversalResponse reversal = responsePair.second;
        Assert.assertNotNull(reversal);
        Assert.assertTrue(reversal.isSuccess());
    }

//    @Test
    public void shouldTopUpAndRedeem() throws Exception {
        final Pair<TopUpCardResponse, RedemptionResponse> responsePair = test.topUpAndRedeem(cardNumber, 5.25);
        Assert.assertNotNull(responsePair);

        final TopUpCardResponse topUp = responsePair.first;
        Assert.assertNotNull(topUp);
        Assert.assertTrue(topUp.isSuccess());

        final RedemptionResponse redemption = responsePair.second;
        Assert.assertNotNull(redemption);
        Assert.assertTrue(redemption.isSuccess());
        Assert.assertNotNull(redemption.getTransactionReference());
    }

//    @Test
    public void shouldTopUpAndCancel() throws Exception {
        final Pair<TopUpCardResponse, CancelTransactionResponse> responsePair = test.topUpAndCancel(cardNumber, 7.55);
        Assert.assertNotNull(responsePair);

        final TopUpCardResponse topUp = responsePair.first;
        Assert.assertNotNull(topUp);
        Assert.assertTrue(topUp.isSuccess());

        final CancelTransactionResponse cancellation = responsePair.second;
        Assert.assertNotNull(cancellation);
        Assert.assertTrue(cancellation.isSuccess());
        Assert.assertNotNull(cancellation.getTransactionReference());
        Assert.assertTrue(cancellation.getRemainingBalance() >= 0.0);
    }

//    @Test
    public void shouldTopUpAndCashBack() throws Exception {
        final Pair<TopUpCardResponse, CashBackResponse> responsePair = test.topUpAndCashBack(cardNumber, 12.25);
        Assert.assertNotNull(responsePair);

        final TopUpCardResponse topUp = responsePair.first;
        Assert.assertNotNull(topUp);
        Assert.assertTrue(topUp.isSuccess());

        final CashBackResponse cashBack = responsePair.second;
        Assert.assertNotNull(cashBack);
        Assert.assertTrue(cashBack.isSuccess());
    }
}