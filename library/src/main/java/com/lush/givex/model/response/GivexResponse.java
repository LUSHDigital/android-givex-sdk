package com.lush.givex.model.response;

import com.google.gson.Gson;
import com.lush.givex.model.JsonRpc;

import java.util.List;

/**
 * @author Matt Allen
 */
public abstract class GivexResponse
{
	public void fromNetworkResponse(String response)
	{
		Gson gson = new Gson();
		JsonRpc json = gson.fromJson(response, JsonRpc.class);
		if (json.getResult() != null)
		{
			parseResult(json.getResult());
		}
		if (json.getError() != null)
		{
			parseError(json.getError());
		}
	}

	public abstract void parseResult(List<String> result);
	public abstract void parseError(List<String> error);
	public abstract boolean isSuccess();
}
