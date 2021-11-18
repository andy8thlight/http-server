package com.andy.httpserver;

import java.io.*;
import java.nio.charset.StandardCharsets;

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
            TheResponse theResponse = routes.lookup(request);

            String body;
            int theStatusCode = theResponse.getStatusCode();
            if (theStatusCode == 404) {
                outputStream.write(notFoundResponse().getBytes(StandardCharsets.UTF_8));
                return;
            }

            if (theStatusCode == 405) {
                outputStream.write(generateMethodNotAllowResponse().getBytes(StandardCharsets.UTF_8));
                return;
            }

            if (request.getMethod() == HttpMethod.HEAD) {
                body = "";
            } else {
                body = theResponse.getBody();
            }

            // TODO: Special handling here
            if (request.getMethod() == HttpMethod.POST) {
                String requestBody = request.getBody();
                body = requestBody;
            }


            outputStream.write(okResponse(body).getBytes(StandardCharsets.UTF_8));
        }
    }

    private String okResponse(String body) {
        return HTTP_1_1 + " 200 OK" + CRLF + CRLF + body;
    }

    private String notFoundResponse() {
        return HTTP_1_1 + " 404 Not Found" + CRLF + CRLF;
    }

    private String generateMethodNotAllowResponse() {
        return HTTP_1_1 + " 405 Not Allowed" + CRLF + CRLF;
    }
}