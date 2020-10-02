package com.lush.givex.integration.helper;

import java.util.List;

public final class GivexTestResponse {
    private static final int RESULT_CODE_INDEX = 1;

    private String jsonrpc;
    private String id;
    private List<String> result;

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getResult() {
        return result;
    }

    public void setResult(List<String> result) {
        this.result = result;
    }

    public boolean resultIsOk() {
        return result != null && result.size() > RESULT_CODE_INDEX && "0".equals(result.get(RESULT_CODE_INDEX));
    }
}
