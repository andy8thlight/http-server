package com.andy.httpserver;

public interface Content {
    String getBody();

    ContentType getContentType();
}
