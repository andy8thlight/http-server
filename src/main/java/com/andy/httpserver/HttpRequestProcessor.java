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
            Route route = routes.getRoute(request);
            TheResponse response = generateResponse(request, route);
            sendResponse(outputStream, response);
        }
    }

    private TheResponse generateResponse(TheRequest request, Route route) {
        if (route == null) {
            return new TheResponse(404, "", HttpStatus.NOT_FOUND);
        }

        if (request.getMethod() != HttpMethod.OPTIONS && request.getMethod() != HttpMethod.HEAD) {
            if (request.getMethod() != route.getHttpMethod()) {
                TheResponse response = new TheResponse(405, "", HttpStatus.NOT_ALLOWED);
                response.setHeader("Allow", route.getAllowHeader());
                return response;
            }
        }

        if (request.getMethod() == HttpMethod.OPTIONS) {
            TheResponse response = new TheResponse(200, route.getBody(), HttpStatus.OK);
            response.setHeader("Allow", route.getAllowHeader());
            return response;
        }

        if (request.getMethod() == HttpMethod.HEAD) {
            return new TheResponse(200, "", HttpStatus.OK);
        }

        if (request.getMethod() == HttpMethod.POST) {
            // TODO: Special handling here
            TheResponse response = new TheResponse(200, "", HttpStatus.OK);
            String requestBody = request.getBody();
            response.setBody(requestBody);
            return response;
        }
        return new TheResponse(200, route.getBody(), HttpStatus.OK);
    }

    void sendResponse(OutputStream outputStream, TheResponse theResponse) throws IOException {
        outputStream.write(theResponse.toString().getBytes(StandardCharsets.UTF_8));
    }
}