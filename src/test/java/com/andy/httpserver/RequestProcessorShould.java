package com.andy.httpserver;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static org.junit.jupiter.api.Assertions.*;

class RequestProcessorShould {


    private final RequestParser requestParser = new RequestParser();

    /*
        TODO:
            "GET / HTTP/1.1\n" +
            "HOST: localhost\n\n";
         */
    @Test
    void return_200_ok() throws IOException, BadRequestException {
        RequestProcessor requestProcessor = new RequestProcessor();
        String requestData = "GET / HTTP/1.1\nHost: localhost\n\n\n";

        ByteArrayInputStream inputStream = StreamHelper.createInputStream(requestData);
        OutputStream outputStream = new ByteArrayOutputStream();

        requestProcessor.processRequests(inputStream, outputStream);

        assertEquals("HTTP/1.1 200 OK\n", outputStream.toString());
    }
}