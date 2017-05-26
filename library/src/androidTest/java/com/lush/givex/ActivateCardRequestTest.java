package com.lush.givex;

import android.support.test.runner.AndroidJUnit4;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lush.givex.model.response.ActivateCardResponse;

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
public class ActivateCardRequestTest extends BaseGivexTest
{
	private CountDownLatch latch;

	@Override
	public void setUp() throws Exception
	{
		super.setUp();
		latch = new CountDownLatch(1);
	}

	@Test
	public void testActivateCardA() throws Exception
	{
		givex.activateCard(CARD_A_NUMBER, 100.00, new Response.Listener<ActivateCardResponse>()
		{
			@Override
			public void onResponse(ActivateCardResponse response)
			{
				latch.countDown();
				assertTrue(response.isSuccess() || response.getResult() == 8);
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
	public void testActivateCardAWithSecurityCode() throws Exception
	{
		givex.activateCard(CARD_A_NUMBER, 100.00, givex.createTransactionCode(), CARD_A_PIN, new Response.Listener<ActivateCardResponse>()
		{
			@Override
			public void onResponse(ActivateCardResponse response)
			{
				latch.countDown();
				assertTrue(response.isSuccess() || response.getResult() == 8);
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
	public void testActivateCardB() throws Exception
	{
		givex.activateCard(CARD_B_NUMBER, 100.00, new Response.Listener<ActivateCardResponse>()
		{
			@Override
			public void onResponse(ActivateCardResponse response)
			{
				latch.countDown();
				assertTrue(response.isSuccess() || response.getResult() == 8);
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
	public void testActivateCardBWithSecurityCode() throws Exception
	{
		givex.activateCard(CARD_B_NUMBER, 100.00, givex.createTransactionCode(), CARD_B_PIN, new Response.Listener<ActivateCardResponse>()
		{
			@Override
			public void onResponse(ActivateCardResponse response)
			{
				latch.countDown();
				assertTrue(response.isSuccess() || response.getResult() == 8);
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
