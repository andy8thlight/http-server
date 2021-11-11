package com.andy.httpserver;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static org.junit.jupiter.api.Assertions.*;

class HttpRequestProcessorShould {


    private final RequestParser requestParser = new RequestParser();

    /*
        TODO:
            "GET / HTTP/1.1\n" +
            "HOST: localhost\n\n";
         */
    @Test
    void return_200_ok() throws IOException, BadRequestException {
        HttpRequestProcessor httpRequestProcessor = new HttpRequestProcessor();
        String requestData = validGetRequest();

        ByteArrayInputStream inputStream = StreamHelper.createInputStream(requestData);
        OutputStream outputStream = new ByteArrayOutputStream();

        httpRequestProcessor.processRequests(inputStream, outputStream);

        assertEquals("HTTP/1.1 200 OK\n", outputStream.toString());
    }

    private String validGetRequest() {
        return "GET / HTTP/1.1\nHost: localhost\n\n";
    }

    @Test
    @Disabled
    void handle_multiple_requests() throws IOException, BadRequestException {
        HttpRequestProcessor httpRequestProcessor = new HttpRequestProcessor();
        String requestData = validGetRequest() + validGetRequest();

        ByteArrayInputStream inputStream = StreamHelper.createInputStream(requestData);
        OutputStream outputStream = new ByteArrayOutputStream();

        httpRequestProcessor.processRequests(inputStream, outputStream);

        assertEquals("HTTP/1.1 200 OK\nHTTP/1.1 200 OK\n", outputStream.toString());
    }
}