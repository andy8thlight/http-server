package com.andy.httpserver;

class RequestLine {
    private final HttpMethod method;
    private final String uri;

    RequestLine(String line) {
        String[] components = line.split("\\s+");
        method = convertVerbToMethod(components[0]);
        uri = components[1];
    }

    private HttpMethod convertVerbToMethod(String verb) {
        return HttpMethod.valueOf(verb);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }
}
