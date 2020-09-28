package com.lush.givex.integration;

import com.lush.givex.integration.helper.GivexTestHttpClient;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;
import java.util.Properties;

public final class GivexIntegrationTest {
    private final String cardNumber = "603628851842001640659";

    private GivexTestHttpClient test;

    @Before
    public void setUp() throws Exception {
        final Properties props = new Properties();
        props.load(new FileReader("../local.properties"));

        final String username = props.getProperty("givex_test_username");
        final String password = props.getProperty("givex_test_password");
        test = new GivexTestHttpClient(username, password, "en");
    }

//    @Test
    public void shouldGetBalance() throws Exception {
        final double balance = test.getBalance(cardNumber);
        Assert.assertTrue(balance >= 0.0);
    }

//    @Test
    public void shouldTopUpAndReverse() throws Exception {
        Assert.assertTrue(test.topUpAndReverse(cardNumber, 14.75));
    }
}