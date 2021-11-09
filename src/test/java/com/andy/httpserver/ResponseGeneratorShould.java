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
        OutputStream out = writeData("data");

        String actual = out.toString();
        assertEquals("data", actual);
    }

    private OutputStream writeData(String data) throws IOException {
        byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.write(bytes);
        return out;
    }
}
