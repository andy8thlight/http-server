package com.andy.httpserver;

import java.io.IOException;
import java.io.OutputStream;

public class PostMethod {
    private final HttpRequestProcessor httpRequestProcessor;

    public PostMethod(HttpRequestProcessor httpRequestProcessor) {
        this.httpRequestProcessor = httpRequestProcessor;
    }

    public void handle(OutputStream outputStream, TheRequest request) throws IOException {
        TheResponse theResponse = new TheResponse(200, "", HttpStatus.OK);
        String requestBody = request.getBody();
        theResponse.setBody(requestBody);
        httpRequestProcessor.sendResponse(outputStream, theResponse);
    }
}