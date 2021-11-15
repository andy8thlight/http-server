package com.andy.httpserver;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class HttpRequestProcessor implements RequestProcessor {
    public static final String CRLF = "\r\n";

    public void processRequests(InputStream inputStream, OutputStream outputStream) throws IOException, BadRequestException {
        if (inputStream != null) {
            RequestParser requestParser = new RequestParser();

            TheRequest request = requestParser.parse(inputStream);

            String body = "";
            if (request.getPath().equals("/simple_get_with_body")) {
                body = "Hello world\n";
            }

            outputStream.write(generateResponse(body).getBytes(StandardCharsets.UTF_8));
        }
    }

    private String generateResponse(String body) {
        return "HTTP/1.1 200 OK" + CRLF + CRLF + body;
    }
}