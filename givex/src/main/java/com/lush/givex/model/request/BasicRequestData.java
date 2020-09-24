package com.lush.givex.model.request;

import java.text.DecimalFormat;

/**
 * @author Matt Allen
 */
public abstract class BasicRequestData {
	private final String code;
	protected final String username, password, languageCode, transactionCode;

	BasicRequestData(String endpoint, String username, String password, String languageCode, String transactionCode) {
		this.code = endpoint;
		this.username = username;
		this.password = password;
		this.languageCode = languageCode;
		this.transactionCode = transactionCode;
	}

	public final String getRequestBody() {
		final String params = getParamsList();
		final String format = "{\"jsonrpc\":\"2.0\",\"id\":\"5\",\"method\":\"%s\",\"params\":[%s]}";

		return String.format(format, code, params);
	}

	protected abstract String getParamsList();

	final String formatAmount(double amount) {
		return new DecimalFormat("0.00").format(amount);
	}
}
