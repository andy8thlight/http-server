package com.andy.httpserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static org.junit.jupiter.api.Assertions.*;

class HttpRequestProcessorShould {

    public static final String CRLF = "\r\n";
    private HttpRequestProcessor httpRequestProcessor;

    @BeforeEach
    void setup() {
        httpRequestProcessor = new HttpRequestProcessor(createTestRoutes());
    }

    private static Routes createTestRoutes() {
        Routes routes = new Routes();

        routes.addRoute("/simple_get_with_body", new Route("/simple_get_with_body", HttpMethod.GET, "Hello world\n"));
        routes.addRoute("/", new Route("/", HttpMethod.GET, ""));
        return routes;
    }

    @Test
    void return_200_ok() throws IOException, BadRequestException {
        String requestData = validGetRequest("/");
        OutputStream outputStream = processRequests(requestData);
        assertEquals("HTTP/1.1 200 OK" + CRLF + CRLF, outputStream.toString());
    }

    @Test
    void return_200_ok_with_body() throws IOException, BadRequestException {
        String requestData = validGetRequest("/simple_get_with_body");
        OutputStream outputStream = processRequests(requestData);
        assertEquals("HTTP/1.1 200 OK" + CRLF + CRLF + "Hello world\n", outputStream.toString());
    }

    @Test
    void return_404_for_non_existant_page() throws BadRequestException, IOException {
        String requestData = validGetRequest("/non_existant");
        OutputStream outputStream = processRequests(requestData);
        assertEquals("HTTP/1.1 404 Not Found" + CRLF + CRLF, outputStream.toString());
    }

    @Test
    void handle_head_request() throws BadRequestException, IOException {
        String requestData = validHeadRequest("/simple_get_with_body");
        OutputStream outputStream = processRequests(requestData);
        assertEquals("HTTP/1.1 200 OK" + CRLF + CRLF, outputStream.toString());
    }

    @Test
    void return_method_not_allowed() throws BadRequestException, IOException {
        String requestData = validPostRequest("/simple_get_with_body");
        OutputStream outputStream = processRequests(requestData);
        assertEquals("HTTP/1.1 405 Not Allowed" + CRLF + CRLF, outputStream.toString());

    }

    private String validGetRequest(final String path) {
        return "GET " + path + " HTTP/1.1\nHost: localhost\n\n";
    }

    private String validHeadRequest(final String path) {
        return "HEAD " + path + " HTTP/1.1\nHost: localhost\n\n";
    }

    private String validPostRequest(final String path) {
        return "POST " + path + " HTTP/1.1\nHost: localhost\n\n";
    }

    private OutputStream processRequests(String requestData) throws IOException, BadRequestException {
        ByteArrayInputStream inputStream = StreamHelper.createInputStream(requestData);
        OutputStream outputStream = new ByteArrayOutputStream();
        httpRequestProcessor.processRequests(inputStream, outputStream);
        return outputStream;
    }
}