package com.lush.givex.model.response;

import com.google.gson.Gson;
import com.lush.givex.model.JsonRpc;

import java.util.List;

/**
 * @author Matt Allen
 */
public abstract class GivexResponse implements ResultConstants {
	public static final int RESULT_OK = 0;
	public static final int RESULT_NONE = -1;

	protected String transactionCode, error;
	protected int result = RESULT_NONE;

	protected boolean parsingComplete;

	protected GivexResponse(String json) {
		final JsonRpc jsonRpc = (new Gson()).fromJson(json, JsonRpc.class);

		if (jsonRpc.getResult() != null) {
			parseCommonResult(jsonRpc.getResult());
		}

		if (jsonRpc.getError() != null) {
			parseError(jsonRpc.getError());
		}
	}

	private void parseCommonResult(List<String> resultList) {
		if (resultList.size() < RESULT_LIST_MIN_LENGTH) {
			error = "Unexpected result length: " + resultList.size();
		} else {
			transactionCode = resultList.get(INDEX_TXN_CODE);
			if (parseResultCode(resultList)) {
				switch (result) {
					case RESULT_NONE:
						break;

					case RESULT_OK:
						parsingComplete = parseResultSafe(resultList);
						break;

					default:
						error = resultList.get(INDEX_ERROR_CODE);
						parsingComplete = parseAdditionalErrorDataSafe(resultList);
						break;
				}
			}
		}
	}

	private boolean parseResultCode(List<String> resultList) {
		final String resultCode = resultList.get(INDEX_RESULT_CODE);

		try {
			result = Integer.parseInt(resultCode);
			return true;
		} catch (Exception e) {
			error = "Unexpected result code: '" + resultCode + "'";
			return false;
		}
	}

	private boolean parseResultSafe(List<String> result) {
		try {
			return parseResult(result);
		} catch (Exception e) {
			error = "Error when parsing the Givex result list: " + e.getMessage();
			return false;
		}
	}

	protected abstract boolean parseResult(List<String> result);
	protected abstract void parseError(List<String> error);

	private boolean parseAdditionalErrorDataSafe(List<String> result) {
		try {
			return parseAdditionalErrorData(result);
		} catch (Exception e) {
			error = "Error when parsing the Givex result list for additional error data: " + e.getMessage();
			return false;
		}
	}

	// Overwrite to set additional values in case of a known Give error.
	protected boolean parseAdditionalErrorData(List<String> result) {
		return true;
	}

	protected final void setUnexpectedLengthError(String callName, int length) {
		result = RESULT_NONE;
		error = "Unexpected result list length in " + callName + " response: " + length;
	}

	public final String getTransactionCode() {
		return transactionCode;
	}

	public final String getError() {
		return error;
	}

	public final int getResult() {
		return result;
	}

	public final boolean isSuccess() {
		return (result == RESULT_OK && parsingComplete);
	}
}