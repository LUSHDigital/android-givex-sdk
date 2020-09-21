package com.lush.givex.model.request;


/**
 * @author Matt Allen
 */
public final class CashBackRequestData extends BasicRequestData {
	private final String cardNumber, securityCode;
	private final double cashBackAmount;

	public CashBackRequestData(String username, String password, String languageCode, String transactionCode, String cardNumber, double cashBackAmount, String securityCode) {
		super("dc_903", username, password, languageCode, transactionCode);

		this.cardNumber = cardNumber;
		this.securityCode = securityCode;
		this.cashBackAmount = cashBackAmount;
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
				formatAmount(cashBackAmount),
				securityCode
		);
	}
}