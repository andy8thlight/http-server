package com.andy.httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

class RequestParser {

    private static final String HOST_HEADER = "Host: ";

    HttpRequest parse(InputStream inputStream) throws IOException, BadRequestException {
        RequestBuilder requestBuilder = buildRequest(inputStream);
        return requestBuilder.createTheRequest();
    }

    private RequestBuilder buildRequest(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        RequestBuilder requestBuilder = new RequestBuilder();
        processHeaders(bufferedReader, requestBuilder);
        processBody(bufferedReader, requestBuilder);
        return requestBuilder;
    }

    private void processHeaders(BufferedReader bufferedReader, RequestBuilder requestBuilder) throws IOException {
        String line;
        while (!(line = bufferedReader.readLine()).isBlank()) {
            processLine(line, requestBuilder);
        }
    }

    private void processLine(String line, RequestBuilder requestBuilder) {
        if (HttpMethod.isHttpVerb(line)) {
            RequestLine requestLine = new RequestLine(line);
            requestBuilder.setMethod(requestLine.getMethod()).setPath(requestLine.getUri());
        }

        if (line.startsWith(HOST_HEADER)) {
            requestBuilder.setHost(extractHost(line));
        }
    }

    private String extractHost(String line) {
        return line.substring(HOST_HEADER.length());
    }

    private void processBody(BufferedReader bufferedReader, RequestBuilder requestBuilder) throws IOException {
        if (requestBuilder.getMethod() == HttpMethod.POST) {
            requestBuilder.setBody(bufferedReader.readLine());
        }
    }
}