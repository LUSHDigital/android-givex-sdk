package com.lush.givex.fallback.impl;

final class GivexUrl {
    final String url;
    final boolean isPrimary;

    GivexUrl(String url, boolean isPrimary) {
        this.url = url;
        this.isPrimary = isPrimary;
    }

    @Override
    public String toString() {
        return url + (isPrimary ? " (Primary)" : " (Secondary)");
    }
}
