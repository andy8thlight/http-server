package com.andy.httpserver;

import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private String body;
    private final HttpStatus httpStatus;
    public static final String CRLF = "\r\n";
    public static final String HTTP_1_1 = "HTTP/1.1";
    private final Map<String, String> headers = new HashMap<>();

    public HttpResponse(HttpStatus httpStatus, String body) {
        this.body = body;
        this.httpStatus = httpStatus;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return HTTP_1_1 + " " + (httpStatus.getCode() + " " + httpStatus.getDescription())
                + getHeaders()
                + CRLF + CRLF
                + getBody();
    }

    private String getHeaders() {
        return headers.keySet().stream().reduce("", (acc, element) -> CRLF + acc + element + ": " + headers.get(element));
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public HttpStatus getStatus() {
        return httpStatus;
    }

    public String getHeader(String name) {
        return headers.get(name);
    }
}
