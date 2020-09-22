package com.lush.givex.model.response;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
	private static final int TXN_CODE_INDEX = 0;
	private static final int RESULT_CODE_INDEX = 1;
	private static final int BALANCE_AMOUNT_INDEX = 2;
	private static final int PINTS_BALANCE_INDEX = 3;
	private static final int EXPIRATION_DATE_INDEX = 4;
	private static final int CURRENCY_CODE_INDEX = 5;

	private static final String NO_EXPIRATION_DATE_VALUE = "None";

	public static final int RESULT_OK = 0;
	public static final int RESULT_NOT_ACTIVATED = 2;
	public static final int RESULT_EXPIRED = 6;

	private String transactionCode, error, currencyCode;
	private int result;
	private double balance, pointsBalance;
	private Date expirationDate;
	private boolean success = false;

	@Override
	public void parseResult(List<String> result) {
		switch (result.size()) {
			case 3:
			case 17:
				parseErrorResult(result);
				break;

			default:
				parseNonErrorResult(result);
				break;
		}
	}

	private void parseErrorResult(List<String> resultList) {
		this.transactionCode = resultList.get(TXN_CODE_INDEX);
		this.result = Integer.parseInt(resultList.get(RESULT_CODE_INDEX));
		this.error = resultList.get(BALANCE_AMOUNT_INDEX);
	}

	private void parseNonErrorResult(List<String> resultList) {
		if (resultList.size() > CURRENCY_CODE_INDEX) {
			parseValidResult(resultList);
		} else {
			this.error = "Unexpected length of 'result' array-node of the Givex get-balance response: " + resultList.size();
		}
	}

	private void parseValidResult(List<String> resultList) {
		transactionCode = resultList.get(TXN_CODE_INDEX);
		result = Integer.parseInt(resultList.get(RESULT_CODE_INDEX));
		balance = Double.parseDouble(resultList.get(BALANCE_AMOUNT_INDEX));
		pointsBalance = Double.parseDouble(resultList.get(PINTS_BALANCE_INDEX));
		expirationDate = parseDate(resultList.get(EXPIRATION_DATE_INDEX));
		currencyCode = resultList.get(CURRENCY_CODE_INDEX);

		success = true;
	}

	private Date parseDate(String value) {
		if (NO_EXPIRATION_DATE_VALUE.equalsIgnoreCase(value)) {
			return null;
		}

		try {
			return (new SimpleDateFormat("yyyy-MM-dd", Locale.US)).parse(value);
		} catch (ParseException e) {
			Log.e(getClass().getName(), "Unexpected date in get-balance Givex response: '" + value + "'", e);
			return null;
		}
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

	public String getError() {
		return error;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public int getResult() {
		return result;
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

	public boolean hasExpired() {
		return (result == RESULT_EXPIRED);
	}
}
