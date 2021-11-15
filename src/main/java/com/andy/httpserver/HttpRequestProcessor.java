package com.andy.httpserver;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class HttpRequestProcessor implements RequestProcessor {
    public void processRequests(InputStream inputStream, OutputStream outputStream) throws IOException, BadRequestException {
        if (inputStream != null) {
            RequestParser requestParser = new RequestParser();

            TheRequest request = requestParser.parse(inputStream);

            String body = "";
            if (request.getPath().equals("/with_body")) {
                body = "Some text body\n";
            }

            String response = "HTTP/1.1 200 OK\r\n\r\n" + body;
            outputStream.write(response.getBytes(StandardCharsets.UTF_8));
        }
    }
}