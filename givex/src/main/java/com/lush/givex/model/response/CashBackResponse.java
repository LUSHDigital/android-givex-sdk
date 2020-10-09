package com.lush.givex.model.response;


import java.util.List;

/**
 * Possible error codes:
 * 0: Worked okay
 * 1: Invalid username/password
 *
 * @author Matt Allen
 */
public final class CashBackResponse extends GivexResponse {

	public CashBackResponse(String json) {
		super(json);
	}

	@Override
	protected boolean parseResult(List<String> result) {
		return true;
	}

	@Override
	protected void parseError(List<String> error) {}
}