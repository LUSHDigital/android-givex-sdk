package com.lush.givex;

public final class GivexConfig {
    public final int timeoutMillis;
    public final String username, password, baseUrl, fallbackUrl, languageCode;

    public GivexConfig(String username, String password, String baseUrl, String fallbackUrl, String languageCode, int timeoutMillis) {
        this.username = username;
        this.password = password;
        this.baseUrl = baseUrl;
        this.fallbackUrl = fallbackUrl;
        this.languageCode = languageCode;
        this.timeoutMillis = timeoutMillis;
    }
}
