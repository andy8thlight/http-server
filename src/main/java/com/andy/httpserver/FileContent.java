package com.andy.httpserver;

public class FileContent implements Content {
    private ContentType contentType;

    public FileContent(ContentType contentType) {
        this.contentType = contentType;
    }

    @Override
    public String getBody() {
        return "<html><body><h1>Hello</h1></body></html>";
    }

    @Override
    public ContentType getContentType() {
        return contentType;
    }
}
