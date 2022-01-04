package com.andy.httpserver;

public enum ContentType {
    TEXT_PLAIN("text/plain"), TEXT_HTML("text/html");

    private String type;

    ContentType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
