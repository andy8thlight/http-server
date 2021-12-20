package com.andy.httpserver;

import java.io.*;

import static java.util.Arrays.*;

public class RequestParser {

    public static final String HOST_HEADER = "Host: ";

    HttpRequest parse(InputStream inputStream) throws IOException, BadRequestException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String line;

        RequestBuilder requestBuilder = new RequestBuilder();
        while (!(line = bufferedReader.readLine()).isBlank()) {
            if (isHttpVerb(line)) {
                String[] components = line.split("\\s+");
                HttpMethod method = convertVerbToMethod(components[0]);
                requestBuilder.setMethod(method).setPath(components[1]);
            }

            if (line.startsWith(HOST_HEADER)) {
                requestBuilder.setHost(extractHost(line));
            }
        }

        if (requestBuilder.getMethod() == HttpMethod.POST) {
            requestBuilder.setBody(bufferedReader.readLine());
        }

        HttpRequest httpRequest = requestBuilder.createTheRequest();
        validateRequest(httpRequest);

        return httpRequest;
    }

    private String extractHost(String line) {
        return line.substring(HOST_HEADER.length());
    }

    private void validateRequest(HttpRequest httpRequest) throws BadRequestException {
        if (httpRequest.getMethod() == null || (httpRequest.getHost() == null || httpRequest.getHost().isBlank())) {
            throw new BadRequestException();
        }
    }

    private boolean isHttpVerb(String line) {
        String[] verbs = new String[]{"POST", "GET", "HEAD", "OPTIONS"};
        return stream(verbs).anyMatch(line::startsWith);
    }

    private HttpMethod convertVerbToMethod(String verb) {
        return HttpMethod.valueOf(verb);
    }
}