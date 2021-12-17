package com.andy.httpserver;

public class Route {
    private final HttpMethod httpMethod;
    private final String body;

    public Route(HttpMethod httpMethod, String body) {
        this.httpMethod = httpMethod;
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getAllowHeader() {
        if (httpMethod == HttpMethod.HEAD) {
            return "HEAD, OPTIONS";
        }
        return httpMethod.name() + ", HEAD, OPTIONS";
    }
}
