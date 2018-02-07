package com.lush.givex.model.response;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author Matt Allen
 */
public class CashBackResponse extends GivexResponse
{
	private String transactionCode, error, transactionReference, receiptMessage;
	private boolean success = false;
	private int result;
	private double newBalance;
	private Date expirationDate;

	@Override
	public void parseResult(List<String> result)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		switch (result.size())
		{
			case 7:
				transactionCode = result.get(0);
				this.result = Integer.parseInt(result.get(1));
				transactionReference = result.get(2);
				newBalance = Double.parseDouble(result.get(3));
				try
				{
					expirationDate = sdf.parse(result.get(4));
				}
				catch (ParseException e)
				{
					e.printStackTrace();
				}
				receiptMessage = result.get(5);
				success = true;
				break;
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

	public String getTransactionReference()
	{
		return transactionReference;
	}

	public String getReceiptMessage()
	{
		return receiptMessage;
	}

	public int getResult()
	{
		return result;
	}

	public double getNewBalance()
	{
		return newBalance;
	}

	public Date getExpirationDate()
	{
		return expirationDate;
	}
}
