package com.lush.givex.model.response;

import com.lush.givex.util.DateFunctions;

import java.util.Date;
import java.util.List;

/**
 * Known result codes:
 *
 * <ul>
 *     <li>Code 13: Wrong amount (But still actually cancels the transaction?)</li>
 *     <li>Code 15: Transaction too old</li>
 * </ul>
 *
 * @author Matt Allen
 */
public final class CancelTransactionResponse extends GivexResponse {
	private static final int RESULT_LIST_LENGTH_WITHOUT_RECEIPT_MSG = 5;

	private String transactionReference, receiptMessage;
	private double remainingBalance;
	private Date expirationDate;

	@Override
	protected void parseResult(List<String> result) {
		if (result.size() == RESULT_LIST_LENGTH_WITHOUT_RECEIPT_MSG) {
			setMainValues(result);
			success = true;
		} else if (result.size() > RESULT_LIST_LENGTH_WITHOUT_RECEIPT_MSG) {
			setMainValues(result);
			receiptMessage = result.get(INDEX_RECEIPT_MESSAGE);
			success = true;
		} else {
			setUnexpectedLengthError("cancel-transaction", result.size());
		}
	}

	private void setMainValues(List<String> result) {
		transactionReference = result.get(INDEX_TXN_REF);
		remainingBalance = Double.parseDouble(result.get(INDEX_BALANCE));
		expirationDate = DateFunctions.parseDate(result.get(INDEX_EXPIRATION_DATE), "cancel-transaction");
	}

	@Override
	protected void parseError(List<String> error) {}

	public String getTransactionReference() {
		return transactionReference;
	}

	public String getReceiptMessage() {
		return receiptMessage;
	}

	public double getRemainingBalance() {
		return remainingBalance;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}
}
