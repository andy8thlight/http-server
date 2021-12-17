package com.andy.httpserver;

public enum HttpMethod {
    POST("POST"), HEAD("HEAD"), GET("GET"), OPTIONS("OPTIONS"), PUT("PUT");

    private String method;

    HttpMethod(String method) {
        this.method = method;
    }
}
