package com.lush.givex.model.response;

import com.lush.givex.util.DateFunctions;

import java.util.Date;
import java.util.List;

/**
 * Possible error codes are stored here as they appear:
 *
 * <ul>
 *     <li>Code 2: Certificate does not exist. Cannot be activated.</li>
 *     <li>Code 8: Card already activated</li>
 *     <li>Code 12: Incorrect amount</li>
 * </ul>
 *
 * @author Matt Allen
 */
public final class ActivateCardResponse extends GivexResponse {
	private static final int RESULT_LIST_LENGTH_WITHOUT_RECEIPT_MSG = 5;

	public static final int RESULT_CERTIFICATE_DOES_NOT_EXIST = 2;
	public static final int RESULT_CARD_ALREADY_ACTIVE = 8;

	private String transactionReference, receiptMessage;
	private double balance;
	private Date expirationDate;

	@Override
	protected void parseResult(List<String> result) {
		if (result.size() == RESULT_LIST_LENGTH_WITHOUT_RECEIPT_MSG) {
			setMainValues(result);
		} else if (result.size() > RESULT_LIST_LENGTH_WITHOUT_RECEIPT_MSG) {
			setMainValues(result);
			receiptMessage = result.get(INDEX_RECEIPT_MESSAGE);
		} else {
			setUnexpectedLengthError("activate-card", result.size());
		}
	}

	private void setMainValues(List<String> result) {
		transactionReference = result.get(INDEX_TXN_REF);
		balance = Double.parseDouble(result.get(INDEX_BALANCE));
		expirationDate = DateFunctions.parseDate(result.get(INDEX_EXPIRATION_DATE), "activate-card");
	}

	@Override
	protected void parseError(List<String> error) {}

	public double getBalance() {
		return balance;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public String getTransactionReference() {
		return transactionReference;
	}

	public String getReceiptMessage() {
		return receiptMessage;
	}

	public boolean doesNotExist() {
		return result == RESULT_CERTIFICATE_DOES_NOT_EXIST;
	}

	public boolean alreadyActive() {
		return result == RESULT_CARD_ALREADY_ACTIVE;
	}
}
