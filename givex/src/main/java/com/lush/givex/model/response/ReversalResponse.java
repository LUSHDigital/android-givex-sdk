package com.lush.givex.model.response;

import java.util.List;

public final class ReversalResponse extends GivexResponse {
	private static final int TXN_CODE_INDEX = 0;
	private static final int RESULT_CODE_INDEX = 1;
	private static final int ERROR_MSG_INDEX = 2;

	private String transactionCode, error;
	private int result;
	private boolean success = false;

	@Override
	public void parseResult(List<String> result) {
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
		if (resultList.size() > ERROR_MSG_INDEX) {
			error = resultList.get(ERROR_MSG_INDEX);
		} else {
			error = "Unexpected result list size in Givex reversal error response: " + resultList.size();
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

	public int getResult() {
		return result;
	}
}
