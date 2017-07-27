package com.lush.givex.model.response;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Possible returns:
 *
 * <ul>
 *     <li>Code 0: All good - the card total was decremented</li>
 *     <li>Code 9: Insufficient funds</li>
 * </ul>
 *
 * @author Matt Allen
 */
public class RedemptionResponse extends GivexResponse
{
	private String transactionCode, transactionReference, receiptMessage, error;
	private Date expirationDate;
	private double remainingBalance;
	private int result;
	private boolean success = false;

	@Override
	public void parseResult(List<String> result)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		switch (result.size())
		{
			case 13:
				transactionCode = result.get(0);
				this.result = Integer.parseInt(result.get(1));
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

			case 4:
				transactionCode = result.get(0);
				this.result = Integer.parseInt(result.get(1));
				error = result.get(2);
				remainingBalance = Double.parseDouble(result.get(3));
				success = false;
				break;

			case 3:
				transactionCode = result.get(0);
				this.result = Integer.parseInt(result.get(1));
				error = result.get(2);
				success = false;
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

	public String getTransactionReference()
	{
		return transactionReference;
	}

	public String getReceiptMessage()
	{
		return receiptMessage;
	}

	public Date getExpirationDate()
	{
		return expirationDate;
	}

	public double getRemainingBalance()
	{
		return remainingBalance;
	}

	public int getResult()
	{
		return result;
	}

	public String getError()
	{
		return error;
	}
}
