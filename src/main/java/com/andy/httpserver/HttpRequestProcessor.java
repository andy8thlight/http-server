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
            TheResponse theResponse;
            if (route == null) {
                theResponse = new TheResponse(404, "", HttpStatus.NOT_FOUND);
                sendResponse(outputStream, theResponse);
                return;
            }

            if (request.getMethod() != HttpMethod.OPTIONS && request.getMethod() != HttpMethod.HEAD) {
                if (request.getMethod() != route.getHttpMethod()) {
                    theResponse = new TheResponse(405, "", HttpStatus.NOT_ALLOWED);
                    theResponse.setHeader("Allow", route.getAllowHeader());
                    sendResponse(outputStream, theResponse);
                    return;
                }
            }

            if (request.getMethod() == HttpMethod.GET) {
                theResponse = new TheResponse(200, route.getBody(), HttpStatus.OK);
                theResponse.setBody(theResponse.getBody());
                sendResponse(outputStream, theResponse);
                return;
            }

            if (request.getMethod() == HttpMethod.OPTIONS) {
                theResponse = new TheResponse(200, route.getBody(), HttpStatus.OK);
                theResponse.setHeader("Allow", route.getAllowHeader());
                sendResponse(outputStream, theResponse);
                return;
            }

            if (request.getMethod() == HttpMethod.HEAD) {
                theResponse = new TheResponse(200, route.getBody(), HttpStatus.OK);
                theResponse.setBody("");
                sendResponse(outputStream, theResponse);
                return;
            }

            if (request.getMethod() == HttpMethod.POST) {
                // TODO: Special handling here
                theResponse = new TheResponse(200, "", HttpStatus.OK);
                String requestBody = request.getBody();
                theResponse.setBody(requestBody);
                sendResponse(outputStream, theResponse);
                return;
            }
        }
    }

    private void sendResponse(OutputStream outputStream, TheResponse theResponse) throws IOException {
        outputStream.write(theResponse.toString().getBytes(StandardCharsets.UTF_8));
    }


}