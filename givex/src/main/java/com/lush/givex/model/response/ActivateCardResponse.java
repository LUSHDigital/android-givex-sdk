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
	private String transactionReference, receiptMessage;
	private double balance;
	private Date expirationDate;

	@Override
	protected void parseResult(List<String> result) {
		transactionCode = result.get(0);
		this.result = Integer.parseInt(result.get(1));
		switch (result.size()) {
			case 5:
				transactionReference = result.get(2);
				balance = Double.parseDouble(result.get(3));
				expirationDate = DateFunctions.parseDate(result.get(4), "activate-card");
				success = true;
				break;

			case 6:
				transactionReference = result.get(2);
				balance = Double.parseDouble(result.get(3));
				expirationDate = DateFunctions.parseDate(result.get(4), "activate-card");
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
}
