package com.lush.givex.model.response;

import java.util.Date;
import java.util.List;

/**
 * @author Matt Allen
 */
public class ActivateCardResponse extends GivexResponse
{
	private String code, transactionCode, error;
	private int result;
	private double cardBalance;
	private Date cardExpiration;

	@Override
	public void parseResult(List<String> result)
	{
		switch (result.size())
		{
			case 7:
				// Success

			case 5
		}
	}

	@Override
	public void parseError(List<String> error)
	{

	}

	public String getCode()
	{
		return code;
	}

	public String getTransactionCode()
	{
		return transactionCode;
	}

	public String getError()
	{
		return error;
	}

	public int getResult()
	{
		return result;
	}

	public double getCardBalance()
	{
		return cardBalance;
	}

	public Date getCardExpiration()
	{
		return cardExpiration;
	}
}
