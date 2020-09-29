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
		transactionCode = resultList.get(TXN_CODE_INDEX);
		result = Integer.parseInt(resultList.get(RESULT_CODE_INDEX));

		if (result == RESULT_OK) {
			success = true;
		} else  {
			setErrorMessage(resultList);
		}
	}

	private void setErrorMessage(List<String> resultList) {
		if (resultList.size() > ERROR_CODE_INDEX) {
			error = resultList.get(ERROR_CODE_INDEX);
		} else {
			error = "Unexpected result list size in Givex reversal error response: " + resultList.size();
		}
	}

	@Override
	protected void parseError(List<String> error) {}
}
