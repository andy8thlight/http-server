package com.andy.httpserver;

public enum HttpStatus {
    OK(200, "OK"),
    NOT_FOUND(404, "Not Found"),
    NOT_ALLOWED(405, "Not Allowed");

    private int code;
    private String description;

    HttpStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }


    public String getDescription() {
        return description;
    }
}
