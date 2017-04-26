package com.lush.givex.model.request;

/**
 * @author Matt Allen
 */
public class ActivateCardRequestData extends BasicRequestData
{
	private double amount;
	private String cardNumber;

	public ActivateCardRequestData(String username, String password, String languageCode, String transactionCode, double amount, String cardNumber)
	{
		super("906", username, password, languageCode, transactionCode);
		this.amount = amount;
		this.cardNumber = cardNumber;
	}

	@Override
	public String getRequestBody()
	{
		String format = "[\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"]";
		return String.format(
				format,
				getCode(),
				getLanguageCode(),
				getTransactionCode(),
				getUsername(),
				getPassword(),
				cardNumber,
				String.valueOf(amount));
	}
}
