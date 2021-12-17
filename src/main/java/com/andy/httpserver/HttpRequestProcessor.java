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

            int theStatusCode = theResponse.getStatusCode();
            if (theStatusCode == 404) {
                sendResponse(outputStream, theResponse);
                return;
            }

            if (request.getMethod() == HttpMethod.OPTIONS) {
                theResponse.setHeader("Allow", "GET, HEAD, OPTIONS");
                sendResponse(outputStream, theResponse);
                return;
            }

            if (theStatusCode == 405) {
                theResponse.setHeader("Allow", "GET, HEAD, OPTIONS");
                sendResponse(outputStream, theResponse);
                return;
            }


            if (request.getMethod() == HttpMethod.HEAD) {
                theResponse.setBody("");
            } else {
                theResponse.setBody(theResponse.getBody());
            }

            // TODO: Special handling here
            if (request.getMethod() == HttpMethod.POST) {
                String requestBody = request.getBody();

                theResponse.setBody(requestBody);
            }


            sendResponse(outputStream, theResponse);
        }

    }

    private void sendResponse(OutputStream outputStream, TheResponse theResponse) throws IOException {
        outputStream.write(theResponse.toString().getBytes(StandardCharsets.UTF_8));
    }


}