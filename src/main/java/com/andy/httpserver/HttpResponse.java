package com.andy.httpserver;

public class HttpResponse {
    private String body = "";
    private final HttpStatus httpStatus;
    public static final String CRLF = "\r\n";
    public static final String HTTP_1_1 = "HTTP/1.1";
    private String key;
    private String value;

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
        if (key != null && value != null) {
            return CRLF + key + ": " + value;
        }
        return "";
    }

    public void addHeader(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public HttpStatus getStatus() {
        return httpStatus;
    }
}
