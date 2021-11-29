package com.andy.httpserver;

public class TheResponse {
    private int statusCode;
    private String body;
    private HttpStatus httpStatus;

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

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
