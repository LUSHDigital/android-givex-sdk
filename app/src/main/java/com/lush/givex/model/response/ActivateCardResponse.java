package com.lush.givex.model.response;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Possible error codes are stored here as they appear:
 *
 * <ul>
 *     <li>Code 8: Card already activated</li>
 *     <li>Code 12: Incorrect amount</li>
 * </ul>
 *
 * @author Matt Allen
 */
public class ActivateCardResponse extends GivexResponse
{
	private String transactionCode, error, transactionReference, receiptMessage;
	private int result;
	private double balance;
	private Date expirationDate;
	private boolean success;

	@Override
	public void parseResult(List<String> result)
	{
		transactionCode = result.get(0);
		this.result = Integer.parseInt(result.get(1));
		SimpleDateFormat sdf = new SimpleDateFormat("", Locale.US); // TODO Date format
		switch (result.size())
		{
			case 5:
				transactionReference = result.get(2);
				balance = Double.parseDouble(result.get(3));
				try
				{
					expirationDate = sdf.parse(result.get(4));
				}
				catch (ParseException e)
				{
					e.printStackTrace();
				}
				success = true;
				break;

			case 6:
				transactionReference = result.get(2);
				balance = Double.parseDouble(result.get(3));
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

			case 3:
				error = result.get(2);
				break;

			default:
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

	public int getResult()
	{
		return result;
	}

	public double getBalance()
	{
		return balance;
	}

	public Date getExpirationDate()
	{
		return expirationDate;
	}

	public String getTransactionReference()
	{
		return transactionReference;
	}

	public String getReceiptMessage()
	{
		return receiptMessage;
	}
}
