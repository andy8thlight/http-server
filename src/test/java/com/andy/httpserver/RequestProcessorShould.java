package com.andy.httpserver;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class RequestProcessorShould {


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

    @Test
    void read_data_into_request() {
        String requestData = "GET / HTTP/1.1\n\n";
        ByteArrayInputStream inputStream = StreamHelper.createInputStream(requestData);

        TheRequest request = parseRequest(inputStream);

        assertEquals("GET", request.getVerb());
    }

    private TheRequest parseRequest(ByteArrayInputStream inputStream) {
        TheRequest request = new TheRequest();
        return request;
    }


}