package com.andy.httpserver;

public class Content {
    private final String body;
    private ContentType contentType;

    public Content(String body, ContentType contentType) {
        this.body = body;
        this.contentType = contentType;
    }

    public Content(ContentType contentType) {
        this.contentType = contentType;
        this.body = "";
    }

    public String getBody() {
        return body;
    }

    public ContentType getContentType() {
        return contentType;
    }
}
