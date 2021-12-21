package com.andy.httpserver;

import java.io.*;

import static java.util.Arrays.*;

public class RequestParser {

    private static final String HOST_HEADER = "Host: ";

    HttpRequest parse(InputStream inputStream) throws IOException, BadRequestException {
        RequestBuilder requestBuilder = buildRequest(inputStream);
        return requestBuilder.createTheRequest();
    }

    private RequestBuilder buildRequest(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        RequestBuilder requestBuilder = new RequestBuilder();
        String line;
        while (!(line = bufferedReader.readLine()).isBlank()) {
            processLine(line, requestBuilder);
        }

        echoRequestBody(bufferedReader, requestBuilder);
        return requestBuilder;
    }

    private void echoRequestBody(BufferedReader bufferedReader, RequestBuilder requestBuilder) throws IOException {
        if (requestBuilder.getMethod() == HttpMethod.POST) {
            requestBuilder.setBody(bufferedReader.readLine());
        }
    }

    private void processLine(String line, RequestBuilder requestBuilder) {
        if (isHttpVerb(line)) {
            String[] components = line.split("\\s+");
            HttpMethod method = convertVerbToMethod(components[0]);
            requestBuilder.setMethod(method).setPath(components[1]);
        }

        if (line.startsWith(HOST_HEADER)) {
            requestBuilder.setHost(extractHost(line));
        }
    }

    private String extractHost(String line) {
        return line.substring(HOST_HEADER.length());
    }

    private boolean isHttpVerb(String line) {
        return stream(HttpMethod.values()).anyMatch(value -> line.startsWith(value.name()));
    }

    private HttpMethod convertVerbToMethod(String verb) {
        return HttpMethod.valueOf(verb);
    }
}