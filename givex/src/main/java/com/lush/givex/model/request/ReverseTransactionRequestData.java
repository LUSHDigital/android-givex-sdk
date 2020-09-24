package com.lush.givex.model.request;


public final class ReverseTransactionRequestData extends BasicRequestData {
	private final String cardNumber, givexAuthCode, securityCode;
	private final double amount;

	public ReverseTransactionRequestData(String username, String password, String languageCode, String transactionCode, String cardNumber, double amount, String givexAuthCode, String securityCode) {
		super("dc_918", username, password, languageCode, transactionCode);

		this.cardNumber = cardNumber;
		this.amount = amount;
		this.givexAuthCode = givexAuthCode;
		this.securityCode = securityCode;
	}

	@Override
	protected String getParamsList() {
		final String format = "\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"";

		return String.format(format,
				languageCode,
				transactionCode,
				username,
				password,
				cardNumber,
				formatAmount(amount),
				givexAuthCode,
				securityCode
		);
	}
}
