package com.andy.httpserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ExampleHttpServerShould {

    @Mock
    private ServerSocket server;
    @Mock
    private Socket clientSocket;
    @Mock
    private SocketHandler socketHandler;

    private ExampleHttpServer exampleHttpServer;

    @BeforeEach
    void setup() throws IOException {
        MockitoAnnotations.openMocks(this);
        exampleHttpServer = new ExampleHttpServer(socketHandler, 7777);
        when(socketHandler.createServerSocket(7777)).thenReturn(server);
        when(server.accept()).thenReturn(clientSocket);
    }

    @Test
    void open_server_socket() throws IOException {
        exampleHttpServer.handle();
        verify(socketHandler).createServerSocket(7777);
    }

    @Test
    void throw_error_if_cannot_create_server_socket() throws IOException {
        when(socketHandler.createServerSocket(anyInt())).thenThrow(IOException.class);

        HttpSocketCreationException exception = assertThrows(HttpSocketCreationException.class, () -> {
            exampleHttpServer.handle();
        });

        assertEquals("Failed to create server socket", exception.getMessage());
    }

    @Test
    void open_socket_to_client_with_server_socket() throws IOException {
        exampleHttpServer.handle();
        verify(server).accept();
    }

    @Test
    void throw_error_if_cannot_create_client_socket() throws IOException {
        when(server.accept()).thenThrow(IOException.class);

        HttpSocketCreationException exception = assertThrows(HttpSocketCreationException.class, () -> {
            exampleHttpServer.handle();
        });

        assertEquals("Failed to create server socket", exception.getMessage());
    }

    @Test
    void get_output_stream() throws IOException {
        exampleHttpServer.handle();
        verify(clientSocket).getOutputStream();
    }

    @Test
    void not_write_data_to_output_stream() throws IOException {
        OutputStream helloStream = new ByteArrayOutputStream();
        when(clientSocket.getOutputStream()).thenReturn(helloStream);

        exampleHttpServer.handle();

        assertEquals("", helloStream.toString());
    }

    @Test
    void read_from_input_stream() throws IOException {
        ByteArrayInputStream inputStream = StreamHelper.createInputStream("some data coming in\n");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String s = bufferedReader.readLine();

        assertEquals("some data coming in", s);
    }

}
