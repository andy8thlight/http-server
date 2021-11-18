package com.andy.httpserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExampleHttpServerShould {

    public static final int PORT_NUMBER = 7777;
    @Mock
    private ServerSocket server;
    @Mock
    private Socket clientSocket;
    @Mock
    private SocketHandler socketHandler;

    private ExampleHttpServer exampleHttpServer;

    @BeforeEach
    void setup() throws IOException {
        Routes routes = new Routes();
        HttpRequestProcessor httpRequestProcessor = new HttpRequestProcessor(routes);
        exampleHttpServer = new ExampleHttpServer(socketHandler, PORT_NUMBER, httpRequestProcessor);
        lenient().when(socketHandler.createServerSocket(PORT_NUMBER)).thenReturn(server);
        lenient().when(server.accept()).thenReturn(clientSocket);
    }

    @Test
    void open_server_socket() throws IOException {
        exampleHttpServer.createServer();
        verify(socketHandler).createServerSocket(PORT_NUMBER);
    }

    @Test
    void throw_error_if_cannot_create_server_socket() throws IOException {
        when(socketHandler.createServerSocket(anyInt())).thenThrow(IOException.class);

        HttpSocketCreationException exception = assertThrows(HttpSocketCreationException.class, () -> exampleHttpServer.createServer());

        assertEquals("Failed to create server socket", exception.getMessage());
    }

    @Test
    void open_socket_to_client_with_server_socket() throws IOException {
        exampleHttpServer.acceptRequest(server);
        verify(server).accept();
    }

    @Test
    void throw_error_if_cannot_create_client_socket() throws IOException {
        when(server.accept()).thenThrow(IOException.class);

        HttpSocketCreationException exception = assertThrows(HttpSocketCreationException.class, () ->
                exampleHttpServer.acceptRequest(server));

        assertEquals("Failed to establish connection to client", exception.getMessage());
    }

    @Test
    void get_output_stream() throws IOException {
        exampleHttpServer.acceptRequest(server);
        verify(clientSocket).getOutputStream();
    }

    @Test
    void not_write_data_to_output_stream() throws IOException {
        OutputStream helloStream = new ByteArrayOutputStream();
        when(clientSocket.getOutputStream()).thenReturn(helloStream);

        exampleHttpServer.acceptRequest(server);

        assertEquals("", helloStream.toString());
    }
}
