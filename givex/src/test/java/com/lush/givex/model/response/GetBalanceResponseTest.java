package com.lush.givex.model.response;

import junit.framework.Assert;

import org.junit.Test;

import java.util.Arrays;

public class GetBalanceResponseTest {

    @Test
    public void shouldParseGetBalanceResult() {
        final String[] result = {"1600769991591", "0", "216.38", "0", "None", "GBP", "", "", "", "", "603628-200164065", "", "", "GBP", "", "", "", "", "", ""};

        final GetBalanceResponse underTest = new GetBalanceResponse();
        underTest.parseResult(Arrays.asList(result));

        Assert.assertEquals("1600769991591", underTest.getTransactionCode());
        Assert.assertEquals(0, underTest.getResult());
        Assert.assertEquals(216.38, underTest.getBalance(), 0.0);
        Assert.assertEquals(0.0, underTest.getPointsBalance(), 0.0);
        Assert.assertNull(underTest.getExpirationDate());
        Assert.assertEquals("GBP", underTest.getCurrencyCode());
        Assert.assertFalse(underTest.hasExpired());
        Assert.assertTrue(underTest.isSuccess());
    }
}
