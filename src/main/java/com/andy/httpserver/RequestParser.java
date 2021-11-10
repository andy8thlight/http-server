package com.andy.httpserver;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class RequestParser {

    public static final String HOST_HEADER = "HOST: ";

    TheRequest parseRequest(ByteArrayInputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String verb = "GET";
        String host = null;
        String line = bufferedReader.readLine();
        if (line.startsWith("POST")) {
            verb = "POST";
        }

        String hostLine = bufferedReader.readLine();
        if (hostLine.startsWith(HOST_HEADER)) {
            host = hostLine.substring(HOST_HEADER.length());
        }


        return new TheRequest(verb, host);
    }
}