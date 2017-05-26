package com.lush.givex.model.request;

import java.text.DecimalFormat;

/**
 * @author Matt Allen
 */
public class CancelTransactionRequestData extends BasicRequestData
{
	private String cardNumber, givexAuthCode, securityCode;
	private double amount;

	public CancelTransactionRequestData(String username, String password, String languageCode, String transactionCode, String cardNumber, double amount, String givexAuthCode, String securityCode)
	{
		super("dc_907", username, password, languageCode, transactionCode);
		this.cardNumber = cardNumber;
		this.amount = amount;
		this.givexAuthCode = givexAuthCode;
		this.securityCode = securityCode;
	}

	@Override
	public String getRequestBody()
	{
		String format = "{\"jsonrpc\":\"2.0\",\"id\":\"5\",\"method\":\"%s\",\"params\":[\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"]}";
		return String.format(
				format,
				getCode(),
				getLanguageCode(),
				getTransactionCode(),
				getUsername(),
				getPassword(),
				cardNumber,
				new DecimalFormat("0.00").format(amount),
				givexAuthCode,
				securityCode
		);
	}
}
