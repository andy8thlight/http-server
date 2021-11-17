package com.andy.httpserver;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpRequestProcessor implements RequestProcessor {
    public static final String CRLF = "\r\n";
    public static final String HTTP_1_1 = "HTTP/1.1";
    private final RequestParser requestParser = new RequestParser();
    private final Routes routes;

    public HttpRequestProcessor(Routes routes) {
        this.routes = routes;
    }

    public void processRequests(InputStream inputStream, OutputStream outputStream) throws IOException, BadRequestException {
        if (inputStream != null) {
            TheRequest request = requestParser.parse(inputStream);
            String response = "";
            String body = routes.lookup(request.getPath());
            if (body != null) {
                if (request.getVerb().equals("HEAD")) {
                    body = "";
                    response = generateOkResponse(body);
                } else if (request.getVerb().equals("POST") && request.getPath().equals("/simple_get_with_body")) {
                    response = generateMethodNotAllowResponse();
                } else {
                    response = generateOkResponse(body);
                }
            } else {
                response = generateNotFoundResponse();
            }
            outputStream.write(response.getBytes(StandardCharsets.UTF_8));
        }
    }

    private String generateOkResponse(String body) {
        return HTTP_1_1 + " 200 OK" + CRLF + CRLF + body;
    }

    private String generateNotFoundResponse() {
        return HTTP_1_1 + " 404 Not Found" + CRLF + CRLF;
    }

    private String generateMethodNotAllowResponse() {
        return HTTP_1_1 + " 405 Not Allowed" + CRLF + CRLF;
    }
}