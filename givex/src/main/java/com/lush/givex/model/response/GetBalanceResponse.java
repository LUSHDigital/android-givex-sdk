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
	private static final String NAME = "get-balance";
	private static final int BALANCE_AMOUNT_INDEX = 2;
	private static final int EXPIRATION_DATE_INDEX = 4;
	private static final int CURRENCY_CODE_INDEX = 5;

	public static final int RESULT_NOT_ACTIVATED = 2;
	public static final int RESULT_EXPIRED = 6;

	private String currencyCode;
	private double balance;
	private Date expirationDate;

	public GetBalanceResponse(String json) {
		super(json);
	}

	@Override
	protected boolean parseResult(List<String> result) {
		if (result.size() > CURRENCY_CODE_INDEX) {
			balance = Double.parseDouble(result.get(BALANCE_AMOUNT_INDEX));
			expirationDate = DateFunctions.parseDate(result.get(EXPIRATION_DATE_INDEX), NAME);
			currencyCode = result.get(CURRENCY_CODE_INDEX);

			return true;
		} else {
			setUnexpectedLengthError(NAME, result.size());

			return false;
		}
	}

	@Override
	protected void parseError(List<String> error) {}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public double getBalance() {
		return balance;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public boolean hasExpired() {
		return (getResult() == RESULT_EXPIRED);
	}
}
