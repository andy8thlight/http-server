package com.andy.httpserver;

import java.io.IOException;
import java.io.OutputStream;

public class HeadMethod {
    private final HttpRequestProcessor httpRequestProcessor;

    public HeadMethod(HttpRequestProcessor httpRequestProcessor) {
        this.httpRequestProcessor = httpRequestProcessor;
    }

    void handle(OutputStream outputStream, Route route) throws IOException {
        TheResponse theResponse = new TheResponse(200, "", HttpStatus.OK);
        httpRequestProcessor.sendResponse(outputStream, theResponse);
    }
}