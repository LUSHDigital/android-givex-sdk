package com.lush.givex.model.request;


public final class ReversalRequestData extends BasicRequestData {
	private final String cardNumber;
	private final double amount;

	public ReversalRequestData(String username, String password, String languageCode, String transactionCode, String cardNumber, double amount) {
		super("dc_918", username, password, languageCode, transactionCode);

		this.cardNumber = cardNumber;
		this.amount = amount;
	}

	@Override
	protected String getParamsList() {
		final String format = "\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"";

		return String.format(format,
				languageCode,
				transactionCode,
				username,
				password,
				cardNumber,
				formatAmount(amount)
		);
	}

	@Override
	public ReversalRequestData getReversalData() {
		return null; // There is no reversal for reversals.
	}

	@Override
	public String name() {
		return "reversal";
	}
}
