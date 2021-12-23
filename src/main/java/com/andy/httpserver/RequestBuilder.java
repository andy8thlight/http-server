package com.andy.httpserver;

class RequestBuilder {
    private String host;
    private String path;
    private HttpMethod method;
    private String body;
    private String contentLength;

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

    public RequestBuilder setContentLength(String contentLength) {
        this.contentLength = contentLength;
        return this;
    }

    HttpRequest build() throws BadRequestException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Host", host);
        httpHeaders.add("Content-Length", contentLength);

        HttpRequest httpRequest = new HttpRequest(method, path, httpHeaders, body);
        httpRequest.validateRequest();
        return httpRequest;
    }
}