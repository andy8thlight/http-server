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
            if (route == null) {
                sendResponse(outputStream, new TheResponse(404, "", HttpStatus.NOT_FOUND));
                return;
            }

            if (request.getMethod() != HttpMethod.OPTIONS && request.getMethod() != HttpMethod.HEAD) {
                if (request.getMethod() != route.getHttpMethod()) {
                    TheResponse response = new TheResponse(405, "", HttpStatus.NOT_ALLOWED);
                    response.setHeader("Allow", route.getAllowHeader());
                    sendResponse(outputStream, response);
                    return;
                }
            }

            if (request.getMethod() == HttpMethod.GET) {
                sendResponse(outputStream, new TheResponse(200, route.getBody(), HttpStatus.OK));
            }

            if (request.getMethod() == HttpMethod.OPTIONS) {
                TheResponse response = new TheResponse(200, route.getBody(), HttpStatus.OK);
                response.setHeader("Allow", route.getAllowHeader());
                sendResponse(outputStream, response);
            }

            if (request.getMethod() == HttpMethod.HEAD) {
                sendResponse(outputStream, new TheResponse(200, "", HttpStatus.OK));
            }

            if (request.getMethod() == HttpMethod.POST) {
                // TODO: Special handling here
                TheResponse response = new TheResponse(200, "", HttpStatus.OK);
                String requestBody = request.getBody();
                response.setBody(requestBody);
                sendResponse(outputStream, response);
            }
        }
    }


    void sendResponse(OutputStream outputStream, TheResponse theResponse) throws IOException {
        outputStream.write(theResponse.toString().getBytes(StandardCharsets.UTF_8));
    }

}