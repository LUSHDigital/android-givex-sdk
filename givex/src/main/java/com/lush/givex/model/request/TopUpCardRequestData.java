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
	protected String getParamsList() {
		final String format = "\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"\",\"\",\"\",\"\",\"\"";

		return String.format(format,
				languageCode,
				transactionCode,
				username,
				password,
				cardNumber,
				formatAmount(topUpAmount),
				securityCode
		);
	}

	@Override
	public ReversalRequestData getReversalData() {
		return new ReversalRequestData(username, password, languageCode, transactionCode, cardNumber, topUpAmount);
	}
}
