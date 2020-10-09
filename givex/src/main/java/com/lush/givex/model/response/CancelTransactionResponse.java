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
	private static final String NAME = "cancel-transaction";

	public static final int RESULT_WRONG_AMOUNT = 13;
	public static final int RESULT_TXN_TOO_OLD = 15;

	private String transactionReference;
	private double remainingBalance;
	private Date expirationDate;

	public CancelTransactionResponse(String json) {
		super(json);
	}

	@Override
	protected boolean parseResult(List<String> result) {
		if (result.size() > INDEX_EXPIRATION_DATE) {
			transactionReference = result.get(INDEX_TXN_REF);
			remainingBalance = Double.parseDouble(result.get(INDEX_BALANCE));
			expirationDate = DateFunctions.parseDate(result.get(INDEX_EXPIRATION_DATE), NAME);

			return true;
		} else {
			setUnexpectedLengthError(NAME, result.size());

			return false;
		}
	}

	@Override
	protected void parseError(List<String> error) {}

	public String getTransactionReference() {
		return transactionReference;
	}

	public double getRemainingBalance() {
		return remainingBalance;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}
}
