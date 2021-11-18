package com.andy.httpserver;

public class TheRequest {
    private String host;
    private String path;
    private HttpMethod method;

    public TheRequest(String host, String path, HttpMethod method) {
        this.host = host;
        this.path = path;
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public String getHost() {
        return host;
    }

    public HttpMethod getMethod() {
        return method;
    }
}
