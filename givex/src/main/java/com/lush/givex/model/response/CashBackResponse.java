package com.lush.givex.model.response;

import com.lush.givex.util.DateFunctions;

import java.util.Date;
import java.util.List;

/**
 * Possible error codes:
 * 0: Worked okay
 * 1: Invalid username/password
 *
 * @author Matt Allen
 */
public final class CashBackResponse extends GivexResponse {
	private String transactionReference, receiptMessage;
	private double newBalance;
	private Date expirationDate;

	@Override
	protected void parseResult(List<String> result) {
		switch (result.size()) {
			case 7:
				transactionCode = result.get(0);
				this.result = Integer.parseInt(result.get(1));
				transactionReference = result.get(2);
				newBalance = Double.parseDouble(result.get(3));
				expirationDate = DateFunctions.parseDate(result.get(4), "cashback");
				receiptMessage = result.get(5);
				success = true;
				break;

			case 3:
				transactionCode = result.get(0);
				this.result = Integer.parseInt(result.get(1));
				this.error = result.get(ERROR_CODE_INDEX);
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

	public double getNewBalance() {
		return newBalance;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}
}
