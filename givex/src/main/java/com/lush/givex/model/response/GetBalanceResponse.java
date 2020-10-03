package com.lush.givex.model.response;


import com.lush.givex.util.DateFunctions;

import java.util.Date;
import java.util.List;

/**
 * Possible result codes:
 *
 * <ul>
 *     <li>Code 0: All good</li>
 *     <li>Code 2: Card not activated</li>
 *     <li>Code 6: Gift card expired</li>
 * </ul>
 *
 * @author Matt Allen
 */
public final class GetBalanceResponse extends GivexResponse {
	private static final int BALANCE_AMOUNT_INDEX = 2;
	private static final int PINTS_BALANCE_INDEX = 3;
	private static final int EXPIRATION_DATE_INDEX = 4;
	private static final int CURRENCY_CODE_INDEX = 5;

	public static final int RESULT_NOT_ACTIVATED = 2;
	public static final int RESULT_EXPIRED = 6;

	private String currencyCode;
	private double balance, pointsBalance;
	private Date expirationDate;

	@Override
	protected void parseResult(List<String> result) {
		if (result.size() > CURRENCY_CODE_INDEX) {
			setValues(result);
			success = true;
		} else {
			setUnexpectedLengthError("get-balance", result.size());
		}
	}

	private void setValues(List<String> resultList) {
		balance = Double.parseDouble(resultList.get(BALANCE_AMOUNT_INDEX));
		pointsBalance = Double.parseDouble(resultList.get(PINTS_BALANCE_INDEX));
		expirationDate = DateFunctions.parseDate(resultList.get(EXPIRATION_DATE_INDEX), "get-balance");
		currencyCode = resultList.get(CURRENCY_CODE_INDEX);
	}

	@Override
	protected void parseError(List<String> error) {}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public double getBalance() {
		return balance;
	}

	public double getPointsBalance() {
		return pointsBalance;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public boolean notActivated() {
		return (result == RESULT_NOT_ACTIVATED);
	}

	public boolean hasExpired() {
		return (result == RESULT_EXPIRED);
	}
}
