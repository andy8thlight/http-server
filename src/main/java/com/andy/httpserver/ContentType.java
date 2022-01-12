package com.andy.httpserver;

public enum ContentType {
    TEXT_PLAIN("text/plain"), TEXT_HTML("text/html"), JSON("application/json"), XML("text/xml");

    private final String type;

    ContentType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
