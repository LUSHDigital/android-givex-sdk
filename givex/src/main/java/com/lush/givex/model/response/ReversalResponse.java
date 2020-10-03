package com.lush.givex.model.response;

import java.util.List;

public final class ReversalResponse extends GivexResponse {
	private static final int TXN_CODE_INDEX = 0;
	private static final int RESULT_CODE_INDEX = 1;

	@Override
	protected void parseResult(List<String> result) {
		if (result.size() <= RESULT_CODE_INDEX) {
			error = "Unexpected result list size in Givex reversal response: " + result.size();
		} else {
			parseResultList(result);
		}
	}

	private void parseResultList(List<String> resultList) {
		success = true;
	}

	@Override
	protected void parseError(List<String> error) {}
}
