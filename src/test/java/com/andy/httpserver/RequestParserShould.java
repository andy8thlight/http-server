package com.andy.httpserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class RequestParserShould {

    private RequestParser requestParser;

    @BeforeEach
    void setup() {
        requestParser = new RequestParser();
    }

    @Test
    void throw_bad_request_when_invalid_request() {
        String requestData = "some junk\n\n";
        ByteArrayInputStream inputStream = StreamHelper.createInputStream(requestData);

        assertThrows(BadRequestException.class, () -> {
            requestParser.parseRequest(inputStream);
        });
    }

    @Test
    void throw_bad_request_when_missing_host_header() throws IOException, BadRequestException {
        String requestData = "GET / HTTP/1.1\n\n";
        ByteArrayInputStream inputStream = StreamHelper.createInputStream(requestData);

        assertThrows(BadRequestException.class, () -> {
            requestParser.parseRequest(inputStream);
        });
    }

    @Test
    void read_host_data_into_request() throws IOException, BadRequestException {
        String requestData = "GET / HTTP/1.1\nHOST: localhost\n\n";
        ByteArrayInputStream inputStream = StreamHelper.createInputStream(requestData);

        TheRequest request = requestParser.parseRequest(inputStream);

        assertEquals("GET", request.getVerb());
        assertEquals("/", request.getPath());
        assertEquals("localhost", request.getHost());
    }

    @Test
    void read_different_host_data_into_request() throws IOException, BadRequestException {
        String requestData = "GET / HTTP/1.1\nHOST: www.google.co.uk\n\n";
        ByteArrayInputStream inputStream = StreamHelper.createInputStream(requestData);

        TheRequest request = requestParser.parseRequest(inputStream);

        assertEquals("GET", request.getVerb());
        assertEquals("/", request.getPath());
        assertEquals("www.google.co.uk", request.getHost());
    }

}