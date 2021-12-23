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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServerShould {

    public static final int PORT_NUMBER = 7777;
    @Mock
    private ServerSocket serverSocket;
    @Mock
    private Socket clientSocket;
    @Mock
    private SocketHandler socketHandler;
    private Server server;


    @BeforeEach
    void setup() throws IOException {
        Routes routes = new Routes();
        HttpRequestProcessor httpRequestProcessor = new HttpRequestProcessor(routes);
        server = new Server(socketHandler, PORT_NUMBER, httpRequestProcessor);
        lenient().when(socketHandler.createServerSocket(PORT_NUMBER)).thenReturn(this.serverSocket);
        lenient().when(this.serverSocket.accept()).thenReturn(clientSocket);
        server.createServer();
    }

    @Test
    void open_server_socket() throws IOException {
        verify(socketHandler).createServerSocket(PORT_NUMBER);
        assertTrue(server.allowsRequests());
    }

    @Test
    void throw_error_if_cannot_create_server_socket() throws IOException {
        when(socketHandler.createServerSocket(anyInt())).thenThrow(IOException.class);

        HttpSocketCreationException exception = assertThrows(HttpSocketCreationException.class, () -> server.createServer());

        assertEquals("Failed to create server socket", exception.getMessage());
    }

    @Test
    void open_socket_to_client_with_server_socket() throws IOException {
        server.acceptRequest();
        verify(serverSocket).accept();
    }

    @Test
    void throw_error_if_cannot_create_client_socket() throws IOException {
        when(serverSocket.accept()).thenThrow(IOException.class);

        HttpSocketCreationException exception = assertThrows(HttpSocketCreationException.class, () ->
                server.acceptRequest());

        assertEquals("Failed to establish connection to client", exception.getMessage());
    }

    @Test
    void get_output_stream() throws IOException {
        server.acceptRequest();
        verify(clientSocket).getOutputStream();
    }

    @Test
    void not_write_data_to_output_stream() throws IOException {
        OutputStream helloStream = new ByteArrayOutputStream();
        when(clientSocket.getOutputStream()).thenReturn(helloStream);

        server.acceptRequest();

        assertEquals("", helloStream.toString());
    }

    @Test
    void should_shutdown_server_and_disallow_further_requests() throws IOException {
        server.stop();

        verify(serverSocket).close();
        assertFalse(server.allowsRequests());
    }
}
