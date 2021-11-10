package com.andy.httpserver;

public class TheRequest {
    private String verb;
    private String host;

    public TheRequest(String verb, String host) {
        this.verb = verb;
        this.host = host;
    }

    public String getVerb() {
        return verb;
    }

    public String getPath() {
        return "/";
    }

    public String getHost() {
        return host;
    }
}
