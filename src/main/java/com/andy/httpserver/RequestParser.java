package com.andy.httpserver;

import java.io.*;

import static java.util.Arrays.*;

public class RequestParser {

    public static final String HOST_HEADER = "Host: ";

    TheRequest parse(InputStream inputStream) throws IOException, BadRequestException {
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

        TheRequest theRequest = requestBuilder.createTheRequest();
        validateRequest(theRequest);

        return theRequest;
    }

    private String extractHost(String line) {
        String host = line.substring(HOST_HEADER.length());
        return host;
    }

    private void validateRequest(TheRequest theRequest) throws BadRequestException {
        if (theRequest.getMethod() == null || (theRequest.getHost() == null || theRequest.getHost().isBlank())) {
            throw new BadRequestException();
        }
    }

    private boolean isHttpVerb(String line) {
        String[] verbs = new String[]{"POST", "GET", "HEAD"};
        return stream(verbs).anyMatch(line::startsWith);
    }

    private HttpMethod convertVerbToMethod(String verb) {
        return HttpMethod.valueOf(verb);
    }
}