package com.lush.givex.model.response;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Known result codes:
 *
 * <ul>
 *     <li>Code 15: Transaction too old</li>
 * </ul>
 *
 * @author Matt Allen
 */
public class CancelCardResponse extends GivexResponse
{
	private String transactionCode, transactionReference, error, receiptMessage;
	private int result;
	private double remainingBalance;
	private Date expirationDate;
	private boolean success = false;

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
				remainingBalance = Double.parseDouble(result.get(3));
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
				remainingBalance = Double.parseDouble(result.get(3));
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
		// TODO
	}

	public String getTransactionCode()
	{
		return transactionCode;
	}

	public String getTransactionReference()
	{
		return transactionReference;
	}

	public String getError()
	{
		return error;
	}

	public String getReceiptMessage()
	{
		return receiptMessage;
	}

	public int getResult()
	{
		return result;
	}

	public double getRemainingBalance()
	{
		return remainingBalance;
	}

	public Date getExpirationDate()
	{
		return expirationDate;
	}

	public boolean isSuccess()
	{
		return success;
	}
}
