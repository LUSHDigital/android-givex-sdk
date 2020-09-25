package com.lush.givex.model.response;

import com.google.gson.Gson;
import com.lush.givex.model.JsonRpc;

import java.util.List;

/**
 * @author Matt Allen
 */
public abstract class GivexResponse {

	public static final int RESULT_OK = 0;

	protected String transactionCode, error;
	protected int result;

	protected boolean success;

	public final void fromNetworkResponse(String response) {
		final Gson gson = new Gson();
		final JsonRpc json = gson.fromJson(response, JsonRpc.class);

		if (json.getResult() != null) {
			parseResult(json.getResult());
		}

		if (json.getError() != null) {
			parseError(json.getError());
		}
	}

	protected abstract void parseResult(List<String> result);
	protected abstract void parseError(List<String> error);

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