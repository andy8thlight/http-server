package com.andy.httpserver;

public enum HttpMethod {
    POST("POST"), HEAD("HEAD"), GET("GET");

    private String method;

    HttpMethod(String method) {
        this.method = method;
    }
}
