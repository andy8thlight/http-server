package com.andy.httpserver;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static org.junit.jupiter.api.Assertions.*;

class HttpRequestProcessorShould {
    @Test
    void return_200_ok() throws IOException, BadRequestException {
        HttpRequestProcessor httpRequestProcessor = new HttpRequestProcessor();
        String requestData = validGetRequest("/");

        ByteArrayInputStream inputStream = StreamHelper.createInputStream(requestData);
        OutputStream outputStream = new ByteArrayOutputStream();

        httpRequestProcessor.processRequests(inputStream, outputStream);

        assertEquals("HTTP/1.1 200 OK\n", outputStream.toString());
    }

    private String validGetRequest(final String path) {
        return "GET " + path + " HTTP/1.1\nHost: localhost\n\n";
    }

}