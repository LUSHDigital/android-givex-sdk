package com.lush.givex;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lush.givex.model.response.GetBalanceResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

/**
 * @author Matt Allen
 */
@RunWith(AndroidJUnit4.class)
public class GetBalanceRequestTest extends BaseGivexTest {
	private CountDownLatch latch;

	@Override @Before
	public void setUp() throws Exception {
		super.setUp();
		latch = new CountDownLatch(1);
	}

	@Test
	public void testGetBalanceCardA() throws Exception {
		Log.v(LOG_TAG, "Get balance of card A");

		givex.getBalance(CARD_A_NUMBER, new Response.Listener<GetBalanceResponse>()
		{
			@Override
			public void onResponse(GetBalanceResponse response)
			{
				latch.countDown();
				assertTrue(response.isSuccess());
				assertTrue(0.00 != response.getBalance());
			}
		}, new Response.ErrorListener()
		{
			@Override
			public void onErrorResponse(VolleyError error)
			{
				latch.countDown();
				fail();
			}
		});

		latch.await();
	}

	@Test
	public void testGetBalanceCardAWithSecurityCode() throws Exception {
		Log.v(LOG_TAG, "Get balance of card A using security code");

		final String transactionCode = String.valueOf(System.currentTimeMillis());
		((VolleyGivex)givex).getBalance(CARD_A_NUMBER, transactionCode, CARD_A_PIN, new Response.Listener<GetBalanceResponse>()
		{
			@Override
			public void onResponse(GetBalanceResponse response)
			{
				latch.countDown();
				assertTrue(response.isSuccess());
				assertTrue(0.00 != response.getBalance());
			}
		}, new Response.ErrorListener()
		{
			@Override
			public void onErrorResponse(VolleyError error)
			{
				latch.countDown();
				fail();
			}
		});

		latch.await();
	}

	@Test
	public void testGetBalanceCardB() throws Exception {
		Log.v(LOG_TAG, "Get balance of card B");

		givex.getBalance(CARD_B_NUMBER, new Response.Listener<GetBalanceResponse>()
		{
			@Override
			public void onResponse(GetBalanceResponse response)
			{
				latch.countDown();
				assertTrue(response.isSuccess());
				assertTrue(0.00 != response.getBalance());
			}
		}, new Response.ErrorListener()
		{
			@Override
			public void onErrorResponse(VolleyError error)
			{
				latch.countDown();
				fail();
			}
		});

		latch.await();
	}

	@Test
	public void testGetBalanceCardBWithSecurityCode() throws Exception {
		Log.v(LOG_TAG, "Get balance of card B using security code");

		final String transactionCode = String.valueOf(System.currentTimeMillis());
		((VolleyGivex)givex).getBalance(CARD_B_NUMBER, transactionCode, CARD_B_PIN, new Response.Listener<GetBalanceResponse>()
		{
			@Override
			public void onResponse(GetBalanceResponse response)
			{
				latch.countDown();
				assertTrue(response.isSuccess());
				assertTrue(0.00 != response.getBalance());
			}
		}, new Response.ErrorListener()
		{
			@Override
			public void onErrorResponse(VolleyError error)
			{
				latch.countDown();
				fail();
			}
		});

		latch.await();
	}
}
