package com.andy.httpserver;

public class TheRequest {
    private String verb;
    private String host;
    private String path;

    public TheRequest(String verb, String host, String path) {
        this.verb = verb;
        this.host = host;
        this.path = path;
    }

    public String getVerb() {
        return verb;
    }

    public String getPath() {
        return path;
    }

    public String getHost() {
        return host;
    }
}
