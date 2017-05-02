package com.lush.givex.model.request;

import java.text.DecimalFormat;

/**
 * @author Matt Allen
 */
public class TopUpCardRequestData extends BasicRequestData
{
	private String cardNumber;
	private double topUpAmount;

	public TopUpCardRequestData(String username, String password, String languageCode, String transactionCode, String cardNumber, double topUpAmount)
	{
		super("dc_905", username, password, languageCode, transactionCode);
		this.topUpAmount = topUpAmount;
		this.cardNumber = cardNumber;
	}

	@Override
	public String getRequestBody()
	{
		String format = "{\"jsonrpc\":\"2.0\",\"id\":\"5\",\"method\":\"%s\",\"params\":[\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"\",\"\",\"\",\"\",\"\",\"\"]}";
		return String.format(
				format,
				getCode(),
				getLanguageCode(),
				getTransactionCode(),
				getUsername(),
				getPassword(),
				cardNumber,
				new DecimalFormat("0.00").format(topUpAmount)
		);
	}
}
