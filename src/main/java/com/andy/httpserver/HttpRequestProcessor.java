package com.andy.httpserver;

import java.io.*;
import java.nio.charset.StandardCharsets;

class HttpRequestProcessor implements RequestProcessor {
    private final RequestParser requestParser = new RequestParser();
    private final Routes routes;

    HttpRequestProcessor(Routes routes) {
        this.routes = routes;
    }

    public void processRequests(InputStream inputStream, OutputStream outputStream) throws IOException, BadRequestException {
        if (inputStream != null) {
            HttpRequest request = requestParser.parse(inputStream);
            HttpResponse response = routes.process(request);
            sendResponse(outputStream, response);
        }
    }

    void sendResponse(OutputStream outputStream, HttpResponse httpResponse) throws IOException {
        outputStream.write(httpResponse.toString().getBytes(StandardCharsets.UTF_8));
    }
}