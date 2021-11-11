package com.andy.httpserver;

import java.io.*;

public class RequestParser {

    public static final String HOST_HEADER = "Host: ";

    TheRequest parseRequest(InputStream inputStream) throws IOException, BadRequestException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String verb = null;
        String host = null;
        String line = bufferedReader.readLine();
        if (line.startsWith("POST")) {
            verb = "POST";
        } else if (line.startsWith("GET")) {
            verb = "GET";
        }

        String hostLine = bufferedReader.readLine();
        if (hostLine.startsWith(HOST_HEADER)) {
            host = hostLine.substring(HOST_HEADER.length());
        }

        if (verb == null || (host == null || host.isBlank())) {
            throw new BadRequestException();
        }


        return new TheRequest(verb, host);
    }
}