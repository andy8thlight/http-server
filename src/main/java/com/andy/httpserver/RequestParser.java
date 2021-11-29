package com.andy.httpserver;

import java.io.*;
import java.util.Arrays;

import static java.util.Arrays.*;

public class RequestParser {

    public static final String HOST_HEADER = "Host: ";

    TheRequest parse(InputStream inputStream) throws IOException, BadRequestException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        HttpMethod method = null;
        String host = null;
        String path = null;

        String line;

        while (!(line = bufferedReader.readLine()).isBlank()) {
            if (isHttpVerb(line)) {
                String[] split = line.split("\\s+");
                String verb = split[0];
                path = split[1];

                method = convertVerbToMethod(verb);
            }

            if (line.startsWith(HOST_HEADER)) {
                host = line.substring(HOST_HEADER.length());
            }
        }

        String body = null;
        if (method == HttpMethod.POST) {
            body = bufferedReader.readLine();
        }

        if (method == null || (host == null || host.isBlank())) {
            throw new BadRequestException();
        }

        return new TheRequest(host, path, method, body);
    }

    private boolean isHttpVerb(String line) {
        String[] verbs = new String[]{"POST", "GET", "HEAD"};
        return stream(verbs).anyMatch(line::startsWith);
    }

    private HttpMethod convertVerbToMethod(String verb) {
        return HttpMethod.valueOf(verb);
    }
}