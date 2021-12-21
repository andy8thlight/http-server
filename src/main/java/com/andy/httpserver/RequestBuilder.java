package com.andy.httpserver;

class RequestBuilder {
    private String host;
    private String path;
    private HttpMethod method;
    private String body;

    RequestBuilder setHost(String host) {
        this.host = host;
        return this;
    }

    RequestBuilder setPath(String path) {
        this.path = path;
        return this;
    }

    RequestBuilder setMethod(HttpMethod method) {
        this.method = method;
        return this;
    }

    HttpMethod getMethod() {
        return method;
    }

    RequestBuilder setBody(String body) {
        this.body = body;
        return this;
    }

    HttpRequest createTheRequest() throws BadRequestException {
        HttpRequest httpRequest = new HttpRequest(method, host, path, body);
        httpRequest.validateRequest();
        return httpRequest;
    }
}