package com.andy.httpserver;

public class TheResponse {
    private final int statusCode;
    private String body = "";
    private final HttpStatus httpStatus;
    public static final String CRLF = "\r\n";
    public static final String HTTP_1_1 = "HTTP/1.1";
    private String key;
    private String value;

    public TheResponse(int statusCode, String body, HttpStatus httpStatus) {
        this.statusCode = statusCode;
        this.body = body;
        this.httpStatus = httpStatus;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return HTTP_1_1 + " " + (statusCode + " " + httpStatus.getDescription())
                + getHeaders()
                + CRLF + CRLF
                + getBody();
    }

    private String getHeaders() {
        if (key != null && value != null) {
            return CRLF + key + ": " + value;
        }
        return "";
    }

    public void setHeader(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
