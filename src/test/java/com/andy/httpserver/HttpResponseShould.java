package com.andy.httpserver;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HttpResponseShould {
    public static final String CRLF = "\r\n";

    @Test
    void convert_body_correctly() {
        HttpResponse response = new HttpResponse(HttpStatus.OK, "blah");
        assertEquals("HTTP/1.1 200 OK" + CRLF + CRLF + "blah", response.toString());
    }

    @Test
    void convert_header_correctly() {
        HttpResponse response = new HttpResponse(HttpStatus.OK, "blah");
        response.addHeader("Location", "localhost");
        assertEquals("HTTP/1.1 200 OK" + CRLF + "Location: localhost" + CRLF + CRLF + "blah", response.toString());
    }

    @Test
    void convert_multiple_headers_correctly() {
        HttpResponse response = new HttpResponse(HttpStatus.OK, "blah");
        response.addHeader("Content-Type", "text/html");
        response.addHeader("Content-Length", "4");
        assertEquals("HTTP/1.1 200 OK" + CRLF +
                "Content-Length: 4" + CRLF +
                "Content-Type: text/html" + CRLF + CRLF + "blah", response.toString());
    }

}