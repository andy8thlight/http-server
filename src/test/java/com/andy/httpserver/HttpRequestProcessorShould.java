package com.andy.httpserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static org.junit.jupiter.api.Assertions.*;

class HttpRequestProcessorShould {

    private HttpRequestProcessor httpRequestProcessor;

    @BeforeEach
    void setup() {
        httpRequestProcessor = new HttpRequestProcessor();
    }

    @Test
    void return_200_ok() throws IOException, BadRequestException {
        String requestData = validGetRequest("/");

        ByteArrayInputStream inputStream = StreamHelper.createInputStream(requestData);
        OutputStream outputStream = new ByteArrayOutputStream();

        httpRequestProcessor.processRequests(inputStream, outputStream);

        assertEquals("HTTP/1.1 200 OK\r\n\r\n", outputStream.toString());
    }

    @Test
    void return_200_ok_with_body() throws IOException, BadRequestException {
        String requestData = validGetRequest("/with_body");

        ByteArrayInputStream inputStream = StreamHelper.createInputStream(requestData);
        OutputStream outputStream = new ByteArrayOutputStream();

        httpRequestProcessor.processRequests(inputStream, outputStream);

        assertEquals("HTTP/1.1 200 OK\r\n\r\nSome text body\n", outputStream.toString());
    }

    private String validGetRequest(final String path) {
        return "GET " + path + " HTTP/1.1\nHost: localhost\n\n";
    }

}