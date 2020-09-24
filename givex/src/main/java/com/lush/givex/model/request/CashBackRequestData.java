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
	protected String getParamsList() {
		final String format = "\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"\",\"\",\"\",\"\",\"\"";

		return String.format(format,
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