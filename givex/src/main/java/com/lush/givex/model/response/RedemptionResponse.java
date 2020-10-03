package com.lush.givex.model.response;

import com.lush.givex.util.DateFunctions;

import java.util.Date;
import java.util.List;

/**
 * Possible returns:
 *
 * <ul>
 *     <li>Code 0: All good - the card total was decremented</li>
 *     <li>Code 9: Insufficient funds</li>
 * </ul>
 *
 * @author Matt Allen
 */
public final class RedemptionResponse extends GivexResponse {
	private static final int RESULT_LIST_LENGTH_WITHOUT_RECEIPT_MSG = 5;

	public static final int RESULT_INSUFFICIENT_FUNDS = 9;

	private String transactionReference, receiptMessage;
	private Date expirationDate;
	private double remainingBalance;

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
			setUnexpectedLengthError("redemption", result.size());
		}
	}

	private void setMainValues(List<String> resultList) {
		transactionReference = resultList.get(INDEX_TXN_REF);
		remainingBalance = Double.parseDouble(resultList.get(INDEX_BALANCE));
		expirationDate = DateFunctions.parseDate(resultList.get(INDEX_EXPIRATION_DATE), "redemption");
	}

	@Override
	protected final void parseAdditionalErrorData(List<String> result) {
		if (result.size() >= INDEX_BALANCE) {
			remainingBalance = Double.parseDouble(result.get(INDEX_BALANCE));
		}
	}

	@Override
	protected void parseError(List<String> error) {}

	public String getTransactionReference() {
		return transactionReference;
	}

	public String getReceiptMessage() {
		return receiptMessage;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public double getRemainingBalance() {
		return remainingBalance;
	}

	public boolean hasInsufficientFunds() {
		return (result == RESULT_INSUFFICIENT_FUNDS);
	}
}