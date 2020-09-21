package com.lush.givex.model.request;

import java.text.DecimalFormat;

/**
 * @author Matt Allen
 */
public final class TopUpCardRequestData extends BasicRequestData {
	private final String cardNumber, securityCode;
	private final double topUpAmount;

	public TopUpCardRequestData(String username, String password, String languageCode, String transactionCode, String cardNumber, double topUpAmount, String securityCode) {
		super("dc_905", username, password, languageCode, transactionCode);

		this.topUpAmount = topUpAmount;
		this.cardNumber = cardNumber;
		this.securityCode = securityCode;
	}

	@Override
	public String getRequestBody() {
		final String format = "{\"jsonrpc\":\"2.0\",\"id\":\"5\",\"method\":\"%s\",\"params\":[\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"\",\"\",\"\",\"\",\"\"]}";
		return String.format(
				format,
				code,
				languageCode,
				transactionCode,
				username,
				password,
				cardNumber,
				formatAmount(topUpAmount),
				securityCode
		);
	}
}
