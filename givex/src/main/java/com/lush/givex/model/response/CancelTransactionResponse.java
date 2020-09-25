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
	private String transactionReference, receiptMessage;
	private double remainingBalance;
	private Date expirationDate;

	@Override
	protected void parseResult(List<String> result) {
		transactionCode = result.get(0);
		this.result = Integer.parseInt(result.get(1));
		switch (result.size()) {
			case 7:
				transactionReference = result.get(2);
				remainingBalance = Double.parseDouble(result.get(3));
				expirationDate = DateFunctions.parseDate(result.get(4), "cancel-transaction");
				receiptMessage = result.get(5);
				success = true;
				break;

			case 3:
				error = result.get(2);
				break;

			default:
				break;
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

	public double getRemainingBalance() {
		return remainingBalance;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}
}
