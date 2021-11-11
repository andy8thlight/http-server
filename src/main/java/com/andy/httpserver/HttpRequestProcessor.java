package com.andy.httpserver;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class HttpRequestProcessor {
    public HttpRequestProcessor() {
    }

    void processRequests(InputStream inputStream, OutputStream outputStream) throws IOException, BadRequestException {
        if (inputStream != null) {
            RequestParser requestParser = new RequestParser();

            requestParser.parse(inputStream);

            String response = "HTTP/1.1 200 OK\n";
            outputStream.write(response.getBytes(StandardCharsets.UTF_8));
        }
    }
}