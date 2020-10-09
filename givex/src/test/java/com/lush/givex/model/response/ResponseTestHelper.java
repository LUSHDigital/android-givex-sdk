package com.lush.givex.model.response;

import java.util.Arrays;
import java.util.Iterator;

public class ResponseTestHelper {

    static String buildJson(String method, String[] result) {
        final String params = buildParams(result);

        return "{\"jsonrpc\":\"2.0\",\"id\":\"5\",\"method\":\"" + method + "\",\"params\":[" + params + "]}";
    }

    private static String buildParams(String[] result) {
        final StringBuilder sb = new StringBuilder();
        final Iterator<String> i = Arrays.asList(result).iterator();
        appendParams(i, sb);

        return sb.toString();
    }

    private static void appendParams(Iterator<String> i, StringBuilder sb) {
        while (i.hasNext()) {
            appendParam(i.next(), sb);
            if (i.hasNext()) {
                sb.append(',');
            }
        }
    }

    private static void appendParam(String p, StringBuilder sb) {
        sb.append('"');
        sb.append(p);
        sb.append('"');
    }

    private ResponseTestHelper() {}
}
