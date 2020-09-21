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
	public String getRequestBody() {
		final String format = "{\"jsonrpc\":\"2.0\",\"id\":\"5\",\"method\":\"%s\",\"params\":[\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"]}";
		return String.format(
				format,
				code,
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
