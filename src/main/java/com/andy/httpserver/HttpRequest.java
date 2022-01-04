package com.andy.httpserver;

public class HttpRequest {
    private final String host;
    private final String path;
    private final HttpMethod method;
    private HttpHeaders httpHeaders;
    private String body;

    public HttpRequest(HttpMethod method, String path, HttpHeaders httpHeaders, String body) {
        this.host = httpHeaders.getHost();
        this.path = path;
        this.method = method;
        this.httpHeaders = httpHeaders;
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

    public Integer getContentLengthHeader() {
        return httpHeaders.getContentLength();
    }

    public void setBody(String body) {
        this.body = body;
    }
}
