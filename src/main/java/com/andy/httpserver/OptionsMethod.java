package com.andy.httpserver;

import java.io.IOException;
import java.io.OutputStream;

public class OptionsMethod {
    private final HttpRequestProcessor httpRequestProcessor;

    public OptionsMethod(HttpRequestProcessor httpRequestProcessor) {
        this.httpRequestProcessor = httpRequestProcessor;
    }

    public void handle(OutputStream outputStream, Route route) throws IOException {
        TheResponse theResponse = new TheResponse(200, route.getBody(), HttpStatus.OK);
        theResponse.setHeader("Allow", route.getAllowHeader());
        httpRequestProcessor.sendResponse(outputStream, theResponse);
    }
}