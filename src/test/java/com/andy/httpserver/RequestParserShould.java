package com.andy.httpserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

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

        assertThrows(BadRequestException.class, () -> requestParser.parse(inputStream));
    }

    @Test
    void throw_bad_request_when_missing_host_header() {
        String requestData = "GET / HTTP/1.1\n\n";
        ByteArrayInputStream inputStream = StreamHelper.createInputStream(requestData);

        assertThrows(BadRequestException.class, () -> requestParser.parse(inputStream));
    }

    @Test
    void read_host_data_into_request() throws IOException, BadRequestException {
        String requestData = "GET / HTTP/1.1\nHost: localhost\n\n";
        ByteArrayInputStream inputStream = StreamHelper.createInputStream(requestData);

        TheRequest request = requestParser.parse(inputStream);

        assertEquals(HttpMethod.GET, request.getMethod());
        assertEquals("/", request.getPath());
        assertEquals("localhost", request.getHost());
    }

    @Test
    void read_different_host_data_into_request() throws IOException, BadRequestException {
        String requestData = "GET / HTTP/1.1\nHost: www.google.co.uk\n\n";
        ByteArrayInputStream inputStream = StreamHelper.createInputStream(requestData);

        TheRequest request = requestParser.parse(inputStream);

        assertEquals(HttpMethod.GET, request.getMethod());
        assertEquals("/", request.getPath());
        assertEquals("www.google.co.uk", request.getHost());
    }

    @Test
    void ignore_unknown_headers() throws IOException, BadRequestException {
        String requestData = "GET / HTTP/1.1\nignore\nignore\nHost: www.google.co.uk\n\n";
        ByteArrayInputStream inputStream = StreamHelper.createInputStream(requestData);

        TheRequest request = requestParser.parse(inputStream);

        assertEquals(HttpMethod.GET, request.getMethod());
        assertEquals("/", request.getPath());
        assertEquals("www.google.co.uk", request.getHost());
    }

    @Test
    void read_path() throws IOException, BadRequestException {
        String requestData = "GET /flibble HTTP/1.1\nHost: www.google.co.uk\n\n";
        ByteArrayInputStream inputStream = StreamHelper.createInputStream(requestData);

        TheRequest request = requestParser.parse(inputStream);

        assertEquals(HttpMethod.GET, request.getMethod());
        assertEquals("/flibble", request.getPath());
        assertEquals("www.google.co.uk", request.getHost());
    }

    @Test
    void handle_head_request() throws BadRequestException, IOException {
        String requestData = "HEAD /flibble HTTP/1.1\nHost: www.google.co.uk\n\n";
        ByteArrayInputStream inputStream = StreamHelper.createInputStream(requestData);

        TheRequest request = requestParser.parse(inputStream);

        assertEquals(HttpMethod.HEAD, request.getMethod());
        assertEquals("/flibble", request.getPath());
        assertEquals("www.google.co.uk", request.getHost());
    }

    @Test
    void handle_post_request() throws BadRequestException, IOException {
        String requestData = "POST /echo HTTP/1.1\nHost: www.google.co.uk\n\necho me";
        ByteArrayInputStream inputStream = StreamHelper.createInputStream(requestData);

        TheRequest request = requestParser.parse(inputStream);

        assertEquals(HttpMethod.POST, request.getMethod());
        assertEquals("/echo", request.getPath());
        assertEquals("www.google.co.uk", request.getHost());
        assertEquals("echo me", request.getBody());
    }


}