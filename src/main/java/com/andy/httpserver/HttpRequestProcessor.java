package com.andy.httpserver;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class HttpRequestProcessor implements RequestProcessor {
    private final RequestParser requestParser = new RequestParser();
    private final Routes routes;

    public HttpRequestProcessor(Routes routes) {
        this.routes = routes;
    }

    public void processRequests(InputStream inputStream, OutputStream outputStream) throws IOException, BadRequestException {
        if (inputStream != null) {
            HttpRequest request = requestParser.parse(inputStream);
            Route route = routes.getRoute(request);
            HttpResponse response = generateResponse(request, route);
            sendResponse(outputStream, response);
        }
    }

    private HttpResponse generateResponse(HttpRequest request, Route route) {
        if (route == null) {
            return new HttpResponse("", HttpStatus.NOT_FOUND);
        }

        if (request.getMethod() != HttpMethod.OPTIONS && request.getMethod() != HttpMethod.HEAD) {
            if (request.getMethod() != route.getHttpMethod()) {
                HttpResponse response = new HttpResponse("", HttpStatus.NOT_ALLOWED);
                response.setHeader("Allow", route.getAllowHeader());
                return response;
            }
        }

        if (request.getMethod() == HttpMethod.OPTIONS) {
            HttpResponse response = new HttpResponse(route.getBody(), HttpStatus.OK);
            response.setHeader("Allow", route.getAllowHeader());
            return response;
        }

        if (request.getMethod() == HttpMethod.HEAD) {
            return new HttpResponse("", HttpStatus.OK);
        }

        if (request.getMethod() == HttpMethod.POST) {
            // TODO: Special handling here
            HttpResponse response = new HttpResponse("", HttpStatus.OK);
            String requestBody = request.getBody();
            response.setBody(requestBody);
            return response;
        }
        return new HttpResponse(route.getBody(), HttpStatus.OK);
    }

    void sendResponse(OutputStream outputStream, HttpResponse httpResponse) throws IOException {
        outputStream.write(httpResponse.toString().getBytes(StandardCharsets.UTF_8));
    }
}