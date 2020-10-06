package com.lush.givex.model.response;


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
	public static final int RESULT_INSUFFICIENT_FUNDS = 9;

	private String transactionReference;

	public RedemptionResponse(String json) {
		super(json);
	}

	@Override
	protected boolean parseResult(List<String> result) {
		if (result.size() > INDEX_TXN_REF) {
			transactionReference = result.get(INDEX_TXN_REF);
			return true;
		} else {
			setUnexpectedLengthError("redemption", result.size());
			return false;
		}
	}

	@Override
	protected void parseError(List<String> error) {}

	public String getTransactionReference() {
		return transactionReference;
	}
}