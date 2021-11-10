package com.andy.httpserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResponseGeneratorShould {
    @Mock
    private Socket clientSocket;
    @Mock
    private OutputStream outputStream;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void send_data() throws IOException {
        ResponseGenerator responseGenerator = new ResponseGenerator();

        OutputStream out = responseGenerator.writeData("data");

        assertEquals("data", out.toString());
    }

}
