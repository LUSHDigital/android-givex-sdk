package com.lush.givex.model.response;

import java.util.List;

public final class ReversalResponse extends GivexResponse {

	public ReversalResponse(String json) {
		super(json);
	}

	@Override
	protected boolean parseResult(List<String> result) {
		return true;
	}

	@Override
	protected void parseError(List<String> error) {}
}
