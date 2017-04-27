package com.lush.givex;

import android.support.test.runner.AndroidJUnit4;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lush.givex.model.response.CancelCardResponse;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

import static junit.framework.Assert.fail;

/**
 * @author Matt Allen
 */
@RunWith(AndroidJUnit4.class)
public class CancelCardRequestTest extends BaseGivexTest
{
	private CountDownLatch latch;

	@Override
	public void setUp() throws Exception
	{
		super.setUp();
		latch = new CountDownLatch(1);
	}

	@Test
	public void testCancelCardRequest() throws Exception
	{
		givex.cancelCard(CARD_A_NUMBER, 100.00, new Response.Listener<CancelCardResponse>()
		{
			@Override
			public void onResponse(CancelCardResponse response)
			{
				latch.countDown();
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
