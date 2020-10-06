package com.lush.givex.model.response;

import java.util.List;

public final class ReversalResponse extends GivexResponse {

	@Override
	protected boolean parseResult(List<String> result) {
		return true;
	}

	@Override
	protected void parseError(List<String> error) {}
}
