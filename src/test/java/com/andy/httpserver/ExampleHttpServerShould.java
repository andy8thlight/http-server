package com.andy.httpserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void setup() {
        MockitoAnnotations.openMocks(this);
        exampleHttpServer = new ExampleHttpServer(socketHandler, 7777);
        when(socketHandler.createServerSocket(7777)).thenReturn(server);
    }

    @Test
    void open_server_socket() throws IOException {
        exampleHttpServer.handle();
        verify(socketHandler).createServerSocket(7777);
    }

    @Test
    void open_socket_to_client_with_server_socket() throws IOException {
        exampleHttpServer.handle();
        verify(server).accept();
    }

    @Test
    void get_output_stream() throws IOException {
        when(server.accept()).thenReturn(clientSocket);
        exampleHttpServer.handle();
        verify(clientSocket).getOutputStream();
    }

    @Test
    void not_write_data_to_output_stream() throws IOException {
        when(server.accept()).thenReturn(clientSocket);
        OutputStream helloStream = new ByteArrayOutputStream();
        when(clientSocket.getOutputStream()).thenReturn(helloStream);

        exampleHttpServer.handle();

        assertEquals("", helloStream.toString());
    }

    @Test
    void read_from_input_stream() throws IOException {
        ByteArrayInputStream inputStream = createInputStream("some data coming in\n");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String s = bufferedReader.readLine();

        assertEquals("some data coming in", s);
    }

    @Test
    void write_data_to_when_spoken_to() throws IOException {
        when(server.accept()).thenReturn(clientSocket);
        ByteArrayInputStream inputStream = createInputStream("some data\n");
        when(clientSocket.getInputStream()).thenReturn(inputStream);
        OutputStream helloStream = new ByteArrayOutputStream();
        when(clientSocket.getOutputStream()).thenReturn(helloStream);

        exampleHttpServer.handle();

        assertEquals("some data", helloStream.toString());
    }

    @Test
    void write_more_data_to_when_spoken_to() throws IOException {
        when(server.accept()).thenReturn(clientSocket);
        ByteArrayInputStream inputStream = createInputStream("some data\nand some more\n");
        when(clientSocket.getInputStream()).thenReturn(inputStream);
        OutputStream helloStream = new ByteArrayOutputStream();
        when(clientSocket.getOutputStream()).thenReturn(helloStream);

        exampleHttpServer.handle();

        assertEquals("some dataand some more", helloStream.toString());
    }

    ByteArrayInputStream createInputStream(String data) {
        return new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
    }

}
