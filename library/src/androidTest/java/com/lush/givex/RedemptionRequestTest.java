package com.lush.givex;

import android.support.test.runner.AndroidJUnit4;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lush.givex.model.response.RedemptionResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

/**
 * @author Matt Allen
 */
@RunWith(AndroidJUnit4.class)
public class RedemptionRequestTest extends BaseGivexTest
{
	private CountDownLatch latch;

	@Override @Before
	public void setUp() throws Exception
	{
		super.setUp();
		latch = new CountDownLatch(1);
	}

	@Test
	public void testRedemption() throws Exception
	{
		givex.redeem(CARD_A_NUMBER, 10.42, new Response.Listener<RedemptionResponse>()
		{
			@Override
			public void onResponse(RedemptionResponse response)
			{
				latch.countDown();
				assertTrue(response.isSuccess());
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
	public void testRedemptionWithSecurityCode() throws Exception
	{
		givex.redeem(CARD_A_NUMBER, 8.51, givex.createTransactionCode(), CARD_A_PIN, new Response.Listener<RedemptionResponse>()
		{
			@Override
			public void onResponse(RedemptionResponse response)
			{
				latch.countDown();
				assertTrue(response.isSuccess());
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
