package com.andy.httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

class RequestParser {

    private static final String HOST_HEADER = "Host: ";
    private static final String CONTENT_LENGTH_HEADER = "Content-Length: ";

    HttpRequest parse(InputStream inputStream) throws IOException, BadRequestException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        RequestBuilder requestBuilder = new RequestBuilder();
        processHeaders(bufferedReader, requestBuilder);
        HttpRequest httpRequest = requestBuilder.build();
        String body = processBody(bufferedReader, httpRequest);
        httpRequest.setBody(body);
        return httpRequest;
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
            requestBuilder.setHost(extractHeader(line, HOST_HEADER));
        }

        if (line.startsWith(CONTENT_LENGTH_HEADER)) {
            requestBuilder.setContentLength(extractHeader(line, CONTENT_LENGTH_HEADER));
        }
    }

    private String extractHeader(String line, String hostHeader) {
        return line.substring(hostHeader.length());
    }

    private String processBody(BufferedReader bufferedReader, HttpRequest httpRequest) throws IOException {
        if (httpRequest.getMethod() == HttpMethod.POST) {
            Integer lengthHeader = httpRequest.getContentLengthHeader();

            char[] body = new char[lengthHeader];
            bufferedReader.read(body, 0, lengthHeader);
            String s = new String(body);
            return s;
        }
        return null;
    }
}