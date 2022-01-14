package com.andy.httpserver;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileContent implements Content {
    private String filename;
    private ContentType contentType;

    public FileContent(String filename, ContentType contentType) {
        this.filename = filename;
        this.contentType = contentType;
    }

    @Override
    public String getBody() {
        URL resource = getClass().getResource("/content/" + filename);
        String file = resource.getFile();
        try {
            return Files.readString(Path.of(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "<html><body><h1>Hello</h1></body></html>";
    }

    @Override
    public ContentType getContentType() {
        return contentType;
    }
}
