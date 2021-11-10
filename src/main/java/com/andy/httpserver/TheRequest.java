package com.andy.httpserver;

public class TheRequest {
    private String verb;

    public TheRequest(String verb) {
        this.verb = verb;
    }

    public String getVerb() {
        return verb;
    }

    public String getPath() {
        return "/";
    }

    public String getHost() {
        return "localhost";
    }
}
