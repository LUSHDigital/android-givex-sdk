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
	private static final int TXN_CODE_INDEX = 0;
	private static final int RESULT_CODE_INDEX = 1;
	private static final int TXN_REF_INDEX = 2;
	private static final int REMAINING_BALANCE_AMOUNT_INDEX = 3;
	private static final int EXPIRATION_DATE_INDEX = 4;
	private static final int RECEIPT_MESSAGE_INDEX = 5;

	public static final int RESULT_INSUFFICIENT_FUNDS = 9;

	private String transactionCode, transactionReference, receiptMessage, error;
	private Date expirationDate;
	private double remainingBalance;
	private int result;
	private boolean success = false;

	@Override
	public void parseResult(List<String> result) {
		switch (result.size()) {
			case 3:
				parseBasicErrorResult(result);
				break;

			case 4:
				parseErrorResultWithBalance(result);
				break;

			default:
				parseNonErrorResult(result);
				break;
		}
	}

	private void parseBasicErrorResult(List<String> resultList) {
		transactionCode = resultList.get(TXN_CODE_INDEX);
		result = Integer.parseInt(resultList.get(RESULT_CODE_INDEX));
		error = resultList.get(TXN_REF_INDEX);
	}

	private void parseErrorResultWithBalance(List<String> resultList) {
		parseBasicErrorResult(resultList);
		remainingBalance = Double.parseDouble(resultList.get(REMAINING_BALANCE_AMOUNT_INDEX));
	}

	private void parseNonErrorResult(List<String> resultList) {
		if (resultList.size() > RECEIPT_MESSAGE_INDEX) {
			parseValidResult(resultList);
		} else {
			this.error = "Unexpected length of 'result' array-node of the Givex redemption response: " + resultList.size();
		}
	}

	private void parseValidResult(List<String> resultList) {
		transactionCode = resultList.get(TXN_CODE_INDEX);
		result = Integer.parseInt(resultList.get(RESULT_CODE_INDEX));
		transactionReference = resultList.get(TXN_REF_INDEX);
		remainingBalance = Double.parseDouble(resultList.get(REMAINING_BALANCE_AMOUNT_INDEX));
		expirationDate = DateFunctions.parseDate(resultList.get(EXPIRATION_DATE_INDEX), "redemption");
		receiptMessage = resultList.get(RECEIPT_MESSAGE_INDEX);

		success = true;
	}

	@Override
	public void parseError(List<String> error) {}

	@Override
	public boolean isSuccess() {
		return success;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

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

	public int getResult() {
		return result;
	}

	public String getError() {
		return error;
	}

	public boolean hasInsufficientFunds() {
		return (result == RESULT_INSUFFICIENT_FUNDS);
	}
}
