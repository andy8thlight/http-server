package com.andy.httpserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;

class RequestParserShould {

    private RequestParser requestParser;

    @BeforeEach
    void setup() {
        requestParser = new RequestParser();
    }

    @Test
    void read_data_into_request() {
        String requestData = "GET / HTTP/1.1\n\n";
        ByteArrayInputStream inputStream = StreamHelper.createInputStream(requestData);

        TheRequest request = requestParser.parseRequest(inputStream);

        assertEquals("GET", request.getVerb());
        assertEquals("/", request.getPath());
    }

    @Test
    void read_host_data_into_request() {
        String requestData = "GET / HTTP/1.1\nHOST: localhost\n\n";
        ByteArrayInputStream inputStream = StreamHelper.createInputStream(requestData);

        TheRequest request = requestParser.parseRequest(inputStream);

        assertEquals("GET", request.getVerb());
        assertEquals("/", request.getPath());
        assertEquals("localhost", request.getHost());
    }

}