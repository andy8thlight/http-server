package com.andy.httpserver;

public class BasicContent implements Content {
    private final String body;
    private final ContentType contentType;

    public BasicContent(String body, ContentType contentType) {
        this.body = body;
        this.contentType = contentType;
    }

    public BasicContent(ContentType contentType) {
        this.contentType = contentType;
        this.body = "";
    }

    @Override
    public String getBody() {
        return body;
    }

    @Override
    public ContentType getContentType() {
        return contentType;
    }
}
