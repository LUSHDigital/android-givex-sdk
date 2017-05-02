package com.lush.givex.model.response;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author Matt Allen
 */
public class GetBalanceResponse extends GivexResponse
{
	private String transactionCode, error, currencyCode;
	private int result;
	private double balance, pointsBalance;
	private Date expirationDate;
	private boolean success = false;

	@Override
	public void parseResult(List<String> result)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		switch (result.size())
		{
			case 18:
				transactionCode = result.get(0);
				this.result = Integer.parseInt(result.get(1));
				balance = Double.parseDouble(result.get(2));
				pointsBalance = Double.parseDouble(result.get(3));
				try
				{
					expirationDate = sdf.parse(result.get(4));
				}
				catch (ParseException e)
				{
					e.printStackTrace();
				}
				currencyCode = result.get(5);
				success = true;
				break;

			// TODO This will also need to account for the card not being activated (Error)
		}
	}

	@Override
	public void parseError(List<String> error)
	{

	}

	@Override
	public boolean isSuccess()
	{
		return success;
	}

	public String getTransactionCode()
	{
		return transactionCode;
	}

	public String getError()
	{
		return error;
	}

	public String getCurrencyCode()
	{
		return currencyCode;
	}

	public int getResult()
	{
		return result;
	}

	public double getBalance()
	{
		return balance;
	}

	public double getPointsBalance()
	{
		return pointsBalance;
	}

	public Date getExpirationDate()
	{
		return expirationDate;
	}
}
