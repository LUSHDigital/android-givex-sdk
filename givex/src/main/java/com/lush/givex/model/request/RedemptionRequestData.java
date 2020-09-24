package com.lush.givex.model.request;


/**
 * @author Matt Allen
 */
public final class RedemptionRequestData extends BasicRequestData {
	private final String cardNumber, securityCode;
	private final double redemptionAmount;

	public RedemptionRequestData(String username, String password, String languageCode, String transactionCode, String cardNumber, double redemptionAmount, String securityCode) {
		super("dc_901", username, password, languageCode, transactionCode);

		this.redemptionAmount = redemptionAmount;
		this.cardNumber = cardNumber;
		this.securityCode = securityCode;
	}

	@Override
	protected String getParamsList() {
		final String format = "\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"\",\"\",\"\",\"\",\"\",\"\"";

		return String.format(format,
				languageCode,
				transactionCode,
				username,
				password,
				cardNumber,
				formatAmount(redemptionAmount),
				securityCode
		);
	}
}
