package com.lush.givex.model.request;


/**
 * @author Matt Allen
 */
public final class CancelTransactionRequestData extends BasicRequestData {
	private final String cardNumber, givexAuthCode, securityCode;
	private final double amount;

	public CancelTransactionRequestData(String username, String password, String languageCode, String transactionCode, String cardNumber, double amount, String givexAuthCode, String securityCode) {
		super("dc_907", username, password, languageCode, transactionCode);

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

	@Override
	public ReversalRequestData getReversalData() {
		return new ReversalRequestData(username, password, languageCode, transactionCode, cardNumber, amount);
	}
}