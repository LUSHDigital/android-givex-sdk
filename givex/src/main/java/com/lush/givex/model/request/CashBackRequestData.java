package com.lush.givex.model.request;

import java.text.DecimalFormat;

/**
 * @author Matt Allen
 */
public class CashBackRequestData extends BasicRequestData
{
	private String cardNumber, securityCode;
	private double cashBackAmount;

	public CashBackRequestData(String username, String password, String languageCode, String transactionCode, String cardNumber, double cashBackAmount, String securityCode)
	{
		super("dc_903", username, password, languageCode, transactionCode);
		this.cardNumber = cardNumber;
		this.securityCode = securityCode;
		this.cashBackAmount = cashBackAmount;
	}

	@Override
	public String getRequestBody()
	{
		String format = "{\"jsonrpc\":\"2.0\",\"id\":\"5\",\"method\":\"%s\",\"params\":[\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"\",\"\",\"\",\"\",\"\"]}";
		return String.format(
				format,
				getCode(),
				getLanguageCode(),
				getTransactionCode(),
				getUsername(),
				getPassword(),
				cardNumber,
				new DecimalFormat("0.00").format(cashBackAmount),
				securityCode
		);
	}
}
