package com.lush.givex.model;

import java.util.List;

/**
 * @author Matt Allen
 */
public class JsonRpc
{
	private String jsonrpc, id;
	private List<String> result, error;

	public JsonRpc(String jsonrpc, String id, List<String> result, List<String> error)
	{
		this.jsonrpc = jsonrpc;
		this.id = id;
		this.result = result;
		this.error = error;
	}

	public String getJsonrpc()
	{
		return jsonrpc;
	}

	public String getId()
	{
		return id;
	}

	public List<String> getResult()
	{
		return result;
	}

	public List<String> getError()
	{
		return error;
	}
}
