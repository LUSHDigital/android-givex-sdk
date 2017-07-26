package com.lush.givex;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Matt Allen
 */
@RunWith(AndroidJUnit4.class)
public abstract class BaseGivexTest
{
	protected static final String LOG_TAG = "GivexTests";

	protected static final String CARD_A_NUMBER = "603628851842001640659";
	protected static final String CARD_A_PIN = "2335";
	protected static final String CARD_B_NUMBER = "603628336542001640668";
	protected static final String CARD_B_PIN = "4617";
	protected static final String CARD_C_NUMBER = "603628439812001640679";
	protected static final String CARD_C_PIN = "5225";
	protected static final String CARD_D_NUMBER = "603628635622001640682";
	protected static final String CARD_D_PIN = "7579";
	protected static final String CARD_E_NUMBER = "603628385222001640691";
	protected static final String CARD_E_PIN = "2587";
	protected static final String CARD_F_NUMBER = "603628356832001640700";
	protected static final String CARD_F_PIN = "2640";

	protected Givex givex;

	@Before
	public void setUp() throws Exception
	{
		givex = new Givex(InstrumentationRegistry.getTargetContext(), "INSERT_USER_HERE", "INSERT_PASSWORD_HERE", Environment.TEST, "en");
	}
}
