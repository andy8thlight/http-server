package com.andy.httpserver;

import java.io.*;

public class RequestParser {

    public static final String HOST_HEADER = "Host: ";

    TheRequest parse(InputStream inputStream) throws IOException, BadRequestException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String verb = null;
        String host = null;
        String path = null;

        String line;
        HttpMethod method = null;

        while (!(line = bufferedReader.readLine()).isBlank()) {
            if (isHttpVerb(line)) {
                String[] split = line.split("\\s+");
                verb = split[0];
                path = split[1];

                method = convertVerbToMethod(verb);
            }

            if (line.startsWith(HOST_HEADER)) {
                host = line.substring(HOST_HEADER.length());
            }
        }

        if (verb == null || (host == null || host.isBlank())) {
            throw new BadRequestException();
        }

        return new TheRequest(verb, host, path, method);
    }

    private boolean isHttpVerb(String line) {
        return line.startsWith("POST") || line.startsWith("GET") || line.startsWith("HEAD");
    }

    private HttpMethod convertVerbToMethod(String verb) {
        return HttpMethod.valueOf(verb);
    }
}