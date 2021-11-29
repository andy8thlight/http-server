package com.andy.httpserver;

import java.io.*;

import static java.util.Arrays.*;

public class RequestParser {

    public static final String HOST_HEADER = "Host: ";

    TheRequest parse(InputStream inputStream) throws IOException, BadRequestException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        HttpMethod method = null;
        String host = null;

        String line;

        RequestBuilder requestBuilder = new RequestBuilder();
        while (!(line = bufferedReader.readLine()).isBlank()) {
            if (isHttpVerb(line)) {
                String[] split = line.split("\\s+");

                method = convertVerbToMethod(split[0]);
                requestBuilder.setMethod(method).setPath(split[1]);
            }

            if (line.startsWith(HOST_HEADER)) {
                host = line.substring(HOST_HEADER.length());
                requestBuilder.setHost(host);
            }
        }

        if (method == HttpMethod.POST) {
            String body = bufferedReader.readLine();
            requestBuilder.setBody(body);
        }

        if (method == null || (host == null || host.isBlank())) {
            throw new BadRequestException();
        }

        return requestBuilder.createTheRequest();
    }

    private boolean isHttpVerb(String line) {
        String[] verbs = new String[]{"POST", "GET", "HEAD"};
        return stream(verbs).anyMatch(line::startsWith);
    }

    private HttpMethod convertVerbToMethod(String verb) {
        return HttpMethod.valueOf(verb);
    }
}