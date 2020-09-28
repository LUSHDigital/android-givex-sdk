package com.lush.givex.model.request;

/**
 * @author Matt Allen
 */
public final class GetBalanceRequestData extends BasicRequestData {
	private final String cardNumber, securityCode;

	public GetBalanceRequestData(String username, String languageCode, String transactionCode, String cardNumber, String securityCode) {
		super("dc_909", username, null, languageCode, transactionCode);

		this.cardNumber = cardNumber;
		this.securityCode = securityCode;
	}

	@Override
	protected String getParamsList() {
		final String format = "\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"";

		return String.format(
				format,
				languageCode,
				transactionCode,
				username,
				cardNumber,
				securityCode
		);
	}

	@Override
	public ReversalRequestData getReversalData() {
		return null; // Reversals do not apply for balance requests.
	}

	@Override
	public String name() {
		return "get-balance";
	}
}
