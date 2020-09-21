package com.lush.givex.model.request;


/**
 * @author Matt Allen
 */
public final class ActivateCardRequestData extends BasicRequestData {
	private final double amount;
	private final String cardNumber, securityCode;

	public ActivateCardRequestData(String username, String password, String languageCode, String transactionCode, double amount, String cardNumber, String securityCode) {
		super("dc_906", username, password, languageCode, transactionCode);

		this.amount = amount;
		this.cardNumber = cardNumber;
		this.securityCode = securityCode;
	}

	@Override
	public String getRequestBody() {
		final String format = "{\"jsonrpc\":\"2.0\",\"id\":\"5\",\"method\":\"%s\",\"params\":[\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"\",\"\",\"%s\"]}";
		return String.format(
				format,
				code,
				languageCode,
				transactionCode,
				username,
				password,
				cardNumber,
				formatAmount(amount),
				securityCode
		);
	}
}
