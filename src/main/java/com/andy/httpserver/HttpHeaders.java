package com.andy.httpserver;

import java.util.HashMap;
import java.util.Map;

public class HttpHeaders {
    private Map<String, String> headers = new HashMap<>();

    public void add(String key, String value) {
        headers.put(key, value);
    }

    public String getHost() {
        return headers.get("Host");
    }

    public Integer getContentLength() {
        String contentLen = headers.get("Content-Length");

        try {
            return Integer.parseInt(contentLen);
        }
        catch (NumberFormatException e) {
        }
        return 0;
    }
}
