package com.lush.givex.model.request;

/**
 * @author Matt Allen
 */
public class GetBalanceRequestData extends BasicRequestData
{
	private String cardNumber;

	public GetBalanceRequestData(String username, String languageCode, String transactionCode, String cardNumber)
	{
		super("dc_909", username, null, languageCode, transactionCode);
		this.cardNumber = cardNumber;
	}

	@Override
	public String getRequestBody()
	{
		String format = "{\"jsonrpc\":\"2.0\",\"id\":\"5\",\"method\":\"%s\",\"params\":[\"%s\",\"%s\",\"%s\",\"%s\",\"\"]}";
		return String.format(
				format,
				getCode(),
				getLanguageCode(),
				getTransactionCode(),
				getUsername(),
				cardNumber
		);
	}
}
