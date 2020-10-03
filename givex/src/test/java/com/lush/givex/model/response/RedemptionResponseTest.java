package com.lush.givex.model.response;



import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class RedemptionResponseTest {

    @Test
    public void shouldParseRedemptionResponse() {
        final String[] result = {"1600785593225", "0", "944601", "75.00", "None", "", "", "", "", "603628-200164065", "", "", "", "", "None"};

        final RedemptionResponse underTest = new RedemptionResponse();
        underTest.parseResult(Arrays.asList(result));

        Assert.assertEquals("944601", underTest.getTransactionReference());
        Assert.assertEquals(75.0, underTest.getRemainingBalance(), 0.0);
        Assert.assertNull(underTest.getExpirationDate());
        Assert.assertEquals("", underTest.getReceiptMessage());
        Assert.assertTrue(underTest.isSuccess());
        Assert.assertFalse(underTest.hasInsufficientFunds());
    }
}
