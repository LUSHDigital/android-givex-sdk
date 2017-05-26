package com.lush.givex.model.request;

import java.text.DecimalFormat;

/**
 * @author Matt Allen
 */
public class RedemptionRequestData extends BasicRequestData
{
	private String cardNumber, securityCode;
	private double amount;

	public RedemptionRequestData(String username, String password, String languageCode, String transactionCode, String cardNumber, double amount, String securityCode)
	{
		super("dc_901", username, password, languageCode, transactionCode);
		this.amount = amount;
		this.cardNumber = cardNumber;
		this.securityCode = securityCode;
	}

	@Override
	public String getRequestBody()
	{
		String format = "{\"jsonrpc\":\"2.0\",\"id\":\"5\",\"method\":\"%s\",\"params\":[\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"\",\"\",\"\",\"\",\"\",\"\"]}";
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
