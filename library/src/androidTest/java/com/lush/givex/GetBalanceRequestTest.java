package com.lush.givex;

import android.support.test.runner.AndroidJUnit4;

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
public class GetBalanceRequestTest extends BaseGivexTest
{
	private CountDownLatch latch;

	@Override @Before
	public void setUp() throws Exception
	{
		super.setUp();
		latch = new CountDownLatch(1);
	}

	@Test
	public void testGetBalance() throws Exception
	{
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
	public void testGetBalanceWithSecurityCode() throws Exception
	{
		givex.getBalance(CARD_A_NUMBER, givex.createTransactionCode(), CARD_A_PIN, new Response.Listener<GetBalanceResponse>()
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
