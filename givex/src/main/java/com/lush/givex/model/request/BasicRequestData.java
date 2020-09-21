package com.lush.givex.model.request;

import java.text.DecimalFormat;

/**
 * @author Matt Allen
 */
public abstract class BasicRequestData {
	protected final String code, username, password, languageCode, transactionCode;

	BasicRequestData(String endpoint, String username, String password, String languageCode, String transactionCode) {
		this.code = endpoint;
		this.username = username;
		this.password = password;
		this.languageCode = languageCode;
		this.transactionCode = transactionCode;
	}

	public abstract String getRequestBody();

	final String formatAmount(double amount) {
		return new DecimalFormat("0.00").format(amount);
	}
}
