package com.lush.givex;

import android.support.test.runner.AndroidJUnit4;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lush.givex.model.response.CashBackResponse;

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
public class CashBackRequestTest extends BaseGivexTest
{
	private CountDownLatch latch;

	@Override @Before
	public void setUp() throws Exception
	{
		super.setUp();
		latch = new CountDownLatch(1);
	}

	@Test
	public void testCashBackFromCardA() throws Exception
	{
		givex.cashBack(CARD_A_NUMBER, 18.00, new Response.Listener<CashBackResponse>()
		{
			@Override
			public void onResponse(CashBackResponse response)
			{
				latch.countDown();
				assertTrue(response.isSuccess());
			}
		}
		, new Response.ErrorListener()
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
