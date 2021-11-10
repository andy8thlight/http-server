package com.andy.httpserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.*;

class RequestParserShould {

    private RequestParser requestParser;

    @BeforeEach
    void setup() {
        requestParser = new RequestParser();
    }

    @Test
    void read_get_data_into_request() throws IOException {
        String requestData = "GET / HTTP/1.1\n\n";
        ByteArrayInputStream inputStream = StreamHelper.createInputStream(requestData);

        TheRequest request = requestParser.parseRequest(inputStream);

        assertEquals("GET", request.getVerb());
        assertEquals("/", request.getPath());
    }

    @Test
    void read_post_data_into_request() throws IOException {
        String requestData = "POST / HTTP/1.1\n\n";
        ByteArrayInputStream inputStream = StreamHelper.createInputStream(requestData);

        TheRequest request = requestParser.parseRequest(inputStream);

        assertEquals("POST", request.getVerb());
        assertEquals("/", request.getPath());
    }

    @Test
    void read_host_data_into_request() throws IOException {
        String requestData = "GET / HTTP/1.1\nHOST: localhost\n\n";
        ByteArrayInputStream inputStream = StreamHelper.createInputStream(requestData);

        TheRequest request = requestParser.parseRequest(inputStream);

        assertEquals("GET", request.getVerb());
        assertEquals("/", request.getPath());
        assertEquals("localhost", request.getHost());
    }

    @Test
    @Disabled
    void get_data_until_double_newline() throws IOException {
        String requestData = "blah\n\nsomefink";
        ByteArrayInputStream inputStream = StreamHelper.createInputStream(requestData);


        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        assertEquals("blah", bufferedReader.readLine());
        assertEquals("", bufferedReader.readLine());
        assertEquals("somefink", bufferedReader.readLine());
    }


}