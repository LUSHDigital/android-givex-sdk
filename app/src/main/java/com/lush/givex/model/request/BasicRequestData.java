package com.lush.givex.model.request;

/**
 * @author Matt Allen
 */
public abstract class BasicRequestData
{
	private String code, username, password, languageCode, transactionCode;

	public BasicRequestData(String endpoint, String username, String password, String languageCode, String transactionCode)
	{
		this.code = endpoint;
		this.username = username;
		this.password = password;
		this.languageCode = languageCode;
		this.transactionCode = transactionCode;
	}

	public String getUsername()
	{
		return username;
	}

	public String getPassword()
	{
		return password;
	}

	public String getLanguageCode()
	{
		return languageCode;
	}

	public String getCode()
	{
		return code;
	}

	public String getTransactionCode()
	{
		return transactionCode;
	}

	public abstract String getRequestBody();
}
