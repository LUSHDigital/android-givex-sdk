package com.lush.givex.model.response;

import com.google.gson.Gson;
import com.lush.givex.model.JsonRpc;

import java.util.List;

/**
 * @author Matt Allen
 */
public abstract class GivexResponse implements ResultConstants {
	public static final int RESULT_OK = 0;

	protected String transactionCode, error;
	protected int result = RESULT_NONE;

	protected boolean success;

	public final void fromNetworkResponse(String response) {
		final JsonRpc json = (new Gson()).fromJson(response, JsonRpc.class);

		if (json.getResult() != null) {
			parseCommonResult(json.getResult());
		}

		if (json.getError() != null) {
			parseError(json.getError());
		}
	}

	private void parseCommonResult(List<String> resultList) {
		if (resultList.size() < RESULT_LIST_MIN_LENGTH) {
			error = "Unexpected result length: " + resultList.size();
		} else {
			transactionCode = resultList.get(INDEX_TXN_CODE);
			parseResultCode(resultList);

			switch (result) {
				case RESULT_NONE:
					break;

				case RESULT_OK:
					parseResult(resultList);
					break;

				default:
					error = resultList.get(INDEX_ERROR_CODE);
					parseAdditionalErrorData(resultList);
					break;
			}
		}
	}

	private void parseResultCode(List<String> resultList) {
		final String resultCode = resultList.get(INDEX_RESULT_CODE);

		try {
			result = Integer.parseInt(resultCode);
		} catch (Exception e) {
			error = "Unexpected result code: '" + resultCode + "'";
		}
	}

	protected abstract void parseResult(List<String> result);
	protected abstract void parseError(List<String> error);

	// Overwrite to set additional values in case of a known Give error.
	protected void parseAdditionalErrorData(List<String> result) {}

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
		return success;
	}
}