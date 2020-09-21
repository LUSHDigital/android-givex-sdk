package com.lush.givex;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lush.givex.model.response.TopUpCardResponse;

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
public class TopUpCardRequestTest extends BaseGivexTest {
	private CountDownLatch latch;

	@Override @Before
	public void setUp() throws Exception {
		super.setUp();
		latch = new CountDownLatch(1);
	}

	@Test
	public void testTopUpCardA() throws Exception {
		Log.v(LOG_TAG, "Top up card A with £75");

		givex.topUp(CARD_A_NUMBER, 75.00, new Response.Listener<TopUpCardResponse>()
		{
			@Override
			public void onResponse(TopUpCardResponse response)
			{
				latch.countDown();
				assertTrue(response.isSuccess());
				assertTrue(response.getNewBalance() > 75.00);
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
	public void testTopUpCardAWithSecurityCode() throws Exception {
		Log.v(LOG_TAG, "Top up card A with £75 using security code");

		final String transactionCode = String.valueOf(System.currentTimeMillis());
		((VolleyGivex)givex).topUp(CARD_A_NUMBER, 75.00, transactionCode, CARD_A_PIN, new Response.Listener<TopUpCardResponse>()
		{
			@Override
			public void onResponse(TopUpCardResponse response)
			{
				latch.countDown();
				assertTrue(response.isSuccess());
				assertTrue(response.getNewBalance() > 75.00);
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
	public void testTopUpCardB() throws Exception {
		Log.v(LOG_TAG, "Top up card B with £75");

		givex.topUp(CARD_B_NUMBER, 75.00, new Response.Listener<TopUpCardResponse>()
		{
			@Override
			public void onResponse(TopUpCardResponse response)
			{
				latch.countDown();
				assertTrue(response.isSuccess());
				assertTrue(response.getNewBalance() > 75.00);
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
	public void testTopUpCardBWithSecurityCode() throws Exception {
		Log.v(LOG_TAG, "Top up card B with £75 using security code");

		final String transactionCode = String.valueOf(System.currentTimeMillis());
		((VolleyGivex)givex).topUp(CARD_B_NUMBER, 75.00, transactionCode, CARD_B_PIN, new Response.Listener<TopUpCardResponse>()
		{
			@Override
			public void onResponse(TopUpCardResponse response)
			{
				latch.countDown();
				assertTrue(response.isSuccess());
				assertTrue(response.getNewBalance() > 75.00);
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
	public void testTopUpCardC() throws Exception {
		Log.v(LOG_TAG, "Top up card C with £75");

		givex.topUp(CARD_C_NUMBER, 75.00, new Response.Listener<TopUpCardResponse>()
		{
			@Override
			public void onResponse(TopUpCardResponse response)
			{
				latch.countDown();
				assertTrue(response.isSuccess());
				assertTrue(response.getNewBalance() > 75.00);
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
	public void testTopUpCardCWithSecurityCode() throws Exception {
		Log.v(LOG_TAG, "Top up card C with £75 using security code");

		final String transactionCode = String.valueOf(System.currentTimeMillis());
		((VolleyGivex)givex).topUp(CARD_C_NUMBER, 75.00, transactionCode, CARD_C_PIN, new Response.Listener<TopUpCardResponse>()
		{
			@Override
			public void onResponse(TopUpCardResponse response)
			{
				latch.countDown();
				assertTrue(response.isSuccess());
				assertTrue(response.getNewBalance() > 75.00);
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
	public void testTopUpCardD() throws Exception {
		Log.v(LOG_TAG, "Top up card D with £75");

		givex.topUp(CARD_D_NUMBER, 75.00, new Response.Listener<TopUpCardResponse>()
		{
			@Override
			public void onResponse(TopUpCardResponse response)
			{
				latch.countDown();
				assertTrue(response.isSuccess());
				assertTrue(response.getNewBalance() > 75.00);
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
	public void testTopUpCardDWithSecurityCode() throws Exception {
		Log.v(LOG_TAG, "Top up card D with £75 using security code");

		final String transactionCode = String.valueOf(System.currentTimeMillis());
		((VolleyGivex)givex).topUp(CARD_D_NUMBER, 75.00, transactionCode, CARD_D_PIN, new Response.Listener<TopUpCardResponse>()
		{
			@Override
			public void onResponse(TopUpCardResponse response)
			{
				latch.countDown();
				assertTrue(response.isSuccess());
				assertTrue(response.getNewBalance() > 75.00);
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
