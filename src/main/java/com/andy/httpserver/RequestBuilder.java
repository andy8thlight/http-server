package com.andy.httpserver;

public class RequestBuilder {
    private String host;
    private String path;
    private HttpMethod method;
    private String body;

    public RequestBuilder setHost(String host) {
        this.host = host;
        return this;
    }

    public RequestBuilder setPath(String path) {
        this.path = path;
        return this;
    }

    public RequestBuilder setMethod(HttpMethod method) {
        this.method = method;
        return this;
    }

    public RequestBuilder setBody(String body) {
        this.body = body;
        return this;
    }

    public TheRequest createTheRequest() {
        return new TheRequest(host, path, method, body);
    }
}