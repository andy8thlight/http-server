package com.andy.httpserver;

public class FileContent implements Content {
    private ContentType contentType;

    public FileContent(ContentType contentType) {
        this.contentType = contentType;
    }

    @Override
    public String getBody() {
        return null;
    }

    @Override
    public ContentType getContentType() {
        return contentType;
    }
}
