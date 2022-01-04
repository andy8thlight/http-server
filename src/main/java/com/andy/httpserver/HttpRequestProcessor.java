package com.andy.httpserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;

class HttpRequestProcessor implements RequestProcessor {
    private final RequestParser requestParser = new RequestParser();
    private final Routes routes;

    private static final Logger logger = LoggerFactory.getLogger(HttpRequestProcessor.class);

    HttpRequestProcessor(Routes routes) {
        this.routes = routes;
    }

    public void processRequests(InputStream inputStream, OutputStream outputStream) throws IOException, BadRequestException {
        if (inputStream != null) {
            HttpRequest request = requestParser.parse(inputStream);
            HttpResponse response = routes.process(request);

            logger.info("Response:\n" + response);

            sendResponse(outputStream, response);
        }
    }

    void sendResponse(OutputStream outputStream, HttpResponse httpResponse) throws IOException {
        outputStream.write(httpResponse.toString().getBytes(StandardCharsets.UTF_8));
    }
}