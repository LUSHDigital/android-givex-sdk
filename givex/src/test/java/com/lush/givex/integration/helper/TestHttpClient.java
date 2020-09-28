package com.lush.givex.integration.helper;


import java.io.IOException;
import java.net.HttpURLConnection;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public final class TestHttpClient {
    private final OkHttpClient client = new OkHttpClient();

    private final String baseUrl;

    public TestHttpClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    String post(String path, String json) throws IOException {
        logRequest("POST", path, json);

        final RequestBody body = RequestBody.create(json.getBytes());
        final Request request = (new Request.Builder()).url(buildUrl(path)).header("Content-Type", "application/json").post(body).build();

        return sendRequest(request);
    }

    private String buildUrl(String path) {
        if (path == null || path.trim().length() == 0) {
            return baseUrl;
        } else {
            return baseUrl + "/" + path.trim();
        }
    }

    private String sendRequest(Request request) throws IOException  {
        try (Response response = client.newCall(request).execute()) {
            if (response.code() == HttpURLConnection.HTTP_OK) {
                return getResponseBody(response);
            } else {
                logError(response);
                throw new RuntimeException("HTTP " + response.code() + " " + response.message());
            }
        }
    }

    private void logRequest(String method, String path, String body) {
        System.out.println(method + " " + path + " HTTP");
        System.out.println(body);
    }

    private void logError(Response response) {
        try {
            System.out.println("HTTP " + response.code() + " " + response.message());
            for (String headerName : response.headers().names()) {
                final String headerValue = response.headers().get(headerName);
                System.out.println(headerName + ": " + headerValue);
            }
            System.out.println(getResponseBody(response));
        } catch (Exception e) {}
    }

    private String getResponseBody(Response response) throws IOException {
        final ResponseBody body = response.body();
        if (body == null) {
            return "";
        } else {
            return body.string();
        }
    }
}
