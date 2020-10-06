package com.lush.givex.model.response;


import java.util.List;

/**
 * @author Matt Allen
 */
public final class TopUpCardResponse extends GivexResponse {
	private String transactionReference;
	private double newBalance;

	@Override
	protected boolean parseResult(List<String> result) {
		if (result.size() > INDEX_BALANCE) {
			transactionReference = result.get(INDEX_TXN_REF);
			newBalance = Double.parseDouble(result.get(INDEX_BALANCE));

			return true;
		} else {
			setUnexpectedLengthError("top-up-card", result.size());

			return false;
		}
	}

	@Override
	protected void parseError(List<String> error) {}

	public String getTransactionReference() {
		return transactionReference;
	}

	public double getNewBalance() {
		return newBalance;
	}
}