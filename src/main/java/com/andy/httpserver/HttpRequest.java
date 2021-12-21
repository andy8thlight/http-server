package com.andy.httpserver;

public class HttpRequest {
    private final String host;
    private final String path;
    private final HttpMethod method;
    private final String body;

    public HttpRequest(HttpMethod method, String host, String path, String body) {
        this.host = host;
        this.path = path;
        this.method = method;
        this.body = body;
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

    public String getBody() {
        return body;
    }

    void validateRequest() throws BadRequestException {
        if (getMethod() == null || (getHost() == null || getHost().isBlank())) {
            throw new BadRequestException();
        }
    }
}
