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
	public String getRequestBody() {
		final String format = "{\"jsonrpc\":\"2.0\",\"id\":\"5\",\"method\":\"%s\",\"params\":[\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"]}";
		return String.format(
				format,
				code,
				languageCode,
				transactionCode,
				username,
				cardNumber,
				securityCode
		);
	}
}
