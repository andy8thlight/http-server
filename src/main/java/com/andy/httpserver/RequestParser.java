package com.andy.httpserver;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class RequestParser {
    TheRequest parseRequest(ByteArrayInputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String verb = "GET";
        String line = bufferedReader.readLine();

        if (line.startsWith("POST")) {
            verb = "POST";
        }

        TheRequest request = new TheRequest(verb);
        return request;
    }
}