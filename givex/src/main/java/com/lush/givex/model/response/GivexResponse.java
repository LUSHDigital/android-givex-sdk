package com.lush.givex.model.response;

import com.google.gson.Gson;
import com.lush.givex.model.JsonRpc;

import java.util.List;

/**
 * @author Matt Allen
 */
public abstract class GivexResponse {

	public static final int RESULT_OK = 0;

	public void fromNetworkResponse(String response) {
		final Gson gson = new Gson();
		final JsonRpc json = gson.fromJson(response, JsonRpc.class);

		if (json.getResult() != null) {
			parseResult(json.getResult());
		}

		if (json.getError() != null) {
			parseError(json.getError());
		}
	}

	public abstract void parseResult(List<String> result);
	public abstract void parseError(List<String> error);
	public abstract boolean isSuccess();
}
