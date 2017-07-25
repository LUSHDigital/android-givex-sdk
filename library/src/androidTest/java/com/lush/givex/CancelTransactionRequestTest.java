package com.lush.givex;

import android.support.test.runner.AndroidJUnit4;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lush.givex.model.response.CancelTransactionResponse;
import com.lush.givex.model.response.GetBalanceResponse;
import com.lush.givex.model.response.RedemptionResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

/**
 * Tests for cancelling increments and redeems from the Givex service.
 *
 * @author Matt Allen
 */
@RunWith(AndroidJUnit4.class)
public class CancelTransactionRequestTest extends BaseGivexTest
{
	private CountDownLatch latch;
	private double startBalance, endBalance;
	private String refOne, refTwo;

	@Override @Before
	public void setUp() throws Exception
	{
		super.setUp();
		latch = new CountDownLatch(1);
	}

	@Test
	public void testCancelFirstRedeem() throws Exception
	{
		// Redeem 2 payments, cancel the first, check balance again to confirm
		latch = new CountDownLatch(1);
		getBalance(CARD_C_NUMBER, new OnBalanceResponseListener()
		{
			@Override
			public void onBalance(double balance)
			{
				startBalance = balance;
				latch.countDown();
			}
		});
		latch.await();
		latch = new CountDownLatch(1);
		redeem(CARD_C_NUMBER, 1.00, new OnRedeemCompleteListener()
		{
			@Override
			public void onRedeemed(String transactionReference)
			{
				if (transactionReference == null)
				{
					fail();
				}
				refOne = transactionReference;
				latch.countDown();
			}
		});
		latch.await();
		latch = new CountDownLatch(1);
		redeem(CARD_C_NUMBER, 2.00, new OnRedeemCompleteListener()
		{
			@Override
			public void onRedeemed(String transactionReference)
			{
				if (transactionReference == null)
				{
					fail();
				}
				refTwo = transactionReference;
				latch.countDown();
			}
		});
		latch.await();
		latch = new CountDownLatch(1);
		givex.cancelTransaction(CARD_C_NUMBER, 1.00, refOne, new Response.Listener<CancelTransactionResponse>()
		{
			@Override
			public void onResponse(CancelTransactionResponse response)
			{
				latch.countDown();
			}
		}, new Response.ErrorListener()
		{
			@Override
			public void onErrorResponse(VolleyError error)
			{
				fail();
			}
		});
		latch.await();
		latch = new CountDownLatch(1);
		getBalance(CARD_C_NUMBER, new OnBalanceResponseListener()
		{
			@Override
			public void onBalance(double balance)
			{
				endBalance = balance;
				latch.countDown();
			}
		});
		latch.await();
		assertEquals(startBalance - 2.00, endBalance);
	}

	private void getBalance(String cardNumber, final OnBalanceResponseListener listener)
	{
		givex.getBalance(cardNumber, new Response.Listener<GetBalanceResponse>()
		{
			@Override
			public void onResponse(GetBalanceResponse response)
			{
				listener.onBalance(response.getBalance());
			}
		}, new Response.ErrorListener()
		{
			@Override
			public void onErrorResponse(VolleyError error)
			{
				listener.onBalance(0.00);
			}
		});
	}

	private void redeem(String cardNumber, double amount, final OnRedeemCompleteListener listener)
	{
		givex.redeem(cardNumber, amount, new Response.Listener<RedemptionResponse>()
		{
			@Override
			public void onResponse(RedemptionResponse response)
			{
				listener.onRedeemed(response.getTransactionReference());
			}
		}, new Response.ErrorListener()
		{
			@Override
			public void onErrorResponse(VolleyError error)
			{
				listener.onRedeemed(null);
			}
		});
	}

	private interface OnBalanceResponseListener
	{
		void onBalance(double balance);
	}

	private interface OnRedeemCompleteListener
	{
		void onRedeemed(String transactionReference);
	}
}
