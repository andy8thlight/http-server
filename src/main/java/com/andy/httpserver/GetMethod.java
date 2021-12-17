package com.andy.httpserver;

import java.io.IOException;
import java.io.OutputStream;

public class GetMethod {
    private final HttpRequestProcessor httpRequestProcessor;

    public GetMethod(HttpRequestProcessor httpRequestProcessor) {
        this.httpRequestProcessor = httpRequestProcessor;
    }

    public void handle(OutputStream outputStream, Route route) throws IOException {
        TheResponse theResponse = new TheResponse(200, route.getBody(), HttpStatus.OK);
        theResponse.setBody(theResponse.getBody());
        httpRequestProcessor.sendResponse(outputStream, theResponse);
    }
}