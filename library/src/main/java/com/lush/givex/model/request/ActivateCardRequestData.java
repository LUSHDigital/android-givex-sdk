package com.lush.givex.model.request;

import java.text.DecimalFormat;

/**
 * @author Matt Allen
 */
public class ActivateCardRequestData extends BasicRequestData
{
	private double amount;
	private String cardNumber, securityCode;

	public ActivateCardRequestData(String username, String password, String languageCode, String transactionCode, double amount, String cardNumber, String securityCode)
	{
		super("dc_906", username, password, languageCode, transactionCode);
		this.amount = amount;
		this.cardNumber = cardNumber;
		this.securityCode = securityCode;
	}

	@Override
	public String getRequestBody()
	{
		String format = "{\"jsonrpc\":\"2.0\",\"id\":\"5\",\"method\":\"%s\",\"params\":[\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"\",\"\",\"%s\"]}";
		return String.format(
				format,
				getCode(),
				getLanguageCode(),
				getTransactionCode(),
				getUsername(),
				getPassword(),
				cardNumber,
				new DecimalFormat("0.00").format(amount),
				securityCode
		);
	}
}
