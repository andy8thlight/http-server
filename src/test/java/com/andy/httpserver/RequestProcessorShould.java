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
    void echo() throws IOException {
        RequestProcessor requestProcessor = new RequestProcessor();

        ByteArrayInputStream inputStream = StreamHelper.createInputStream("somefink");
        OutputStream outputStream = new ByteArrayOutputStream();

        requestProcessor.processRequests(inputStream, outputStream);

        assertEquals("somefink", outputStream.toString());
    }
}