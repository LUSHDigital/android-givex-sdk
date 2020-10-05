package com.lush.givex.model.response;

import com.lush.givex.util.DateFunctions;

import java.util.Date;
import java.util.List;

/**
 * @author Matt Allen
 */
public final class TopUpCardResponse extends GivexResponse {
	private static final int RESULT_LIST_LENGTH_WITHOUT_RECEIPT_MSG = 5;

	private String transactionReference, receiptMessage;
	private double newBalance;
	private Date expirationDate;

	@Override
	protected void parseResult(List<String> result) {
		if (result.size() == RESULT_LIST_LENGTH_WITHOUT_RECEIPT_MSG) {
			setMainValues(result);
		} else if (result.size() > RESULT_LIST_LENGTH_WITHOUT_RECEIPT_MSG) {
			setMainValues(result);
			receiptMessage = result.get(INDEX_RECEIPT_MESSAGE);
		} else {
			setUnexpectedLengthError("top-up-card", result.size());
		}
	}

	private void setMainValues(List<String> result) {
		transactionReference = result.get(INDEX_TXN_REF);
		newBalance = Double.parseDouble(result.get(INDEX_BALANCE));
		expirationDate = DateFunctions.parseDate(result.get(INDEX_EXPIRATION_DATE), "top-up-card");
	}

	@Override
	protected void parseError(List<String> error) {}

	public String getTransactionReference() {
		return transactionReference;
	}

	@Deprecated()
	public String getReceiptMessage() {
		return receiptMessage;
	}

	public double getNewBalance() {
		return newBalance;
	}

	@Deprecated()
	public Date getExpirationDate() {
		return expirationDate;
	}
}