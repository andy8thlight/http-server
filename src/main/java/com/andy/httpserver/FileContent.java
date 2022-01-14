package com.andy.httpserver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileContent implements Content {
    private final String filename;
    private final ContentType contentType;
    private final String contentRoot;

    //TODO: passing across a filename here is not great otherwise we'd have to have a FileContent object for each
    // file on our contentRoot.
    public FileContent(String filename, ContentType contentType, String contentRoot) {
        this.filename = filename;
        this.contentType = contentType;
        this.contentRoot = contentRoot;
    }

    @Override
    public String getBody() {
        Path path = Path.of(contentRoot, filename);
        String file = path.toString();

        try {
            //TODO: We shouldn't be storing into a string, as we want to send bytes back down the socket.
            // plus we also want to stream it
            return Files.readString(Path.of(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //TODO: Here really should be a 404 because no content was found
        return "<html><body><h1>Hello</h1></body></html>";
    }

    @Override
    public ContentType getContentType() {
        return contentType;
    }
}
