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
	public String getRequestBody() {
		final String format = "{\"jsonrpc\":\"2.0\",\"id\":\"5\",\"method\":\"%s\",\"params\":[\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"\",\"\",\"\",\"\",\"\",\"\"]}";
		return String.format(
				format,
				code,
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
