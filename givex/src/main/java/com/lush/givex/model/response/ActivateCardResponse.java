package com.lush.givex.model.response;


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
	public static final int RESULT_CERTIFICATE_DOES_NOT_EXIST = 2; // Cannot be activated.
	public static final int RESULT_CARD_ALREADY_ACTIVATED = 8;
	public static final int RESULT_INCORRECT_AMOUNT = 12;

	@Override
	protected boolean parseResult(List<String> result) {
		return true;
	}

	@Override
	protected void parseError(List<String> error) {}

	public boolean doesNotExist() {
		return result == RESULT_CERTIFICATE_DOES_NOT_EXIST;
	}
}
