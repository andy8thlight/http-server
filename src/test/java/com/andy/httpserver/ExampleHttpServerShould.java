package com.andy.httpserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

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
        exampleHttpServer = new ExampleHttpServer(socketHandler);
    }

    @Test
    void open_server_socket() throws IOException {
        exampleHttpServer.handle();
        verify(socketHandler).createServerSocket();
    }

    @Test
    void open_socket_to_client_with_server_socket() throws IOException {
        when(socketHandler.createServerSocket()).thenReturn(server);
        exampleHttpServer.handle();
        verify(server).accept();
    }

    @Test
    void get_output_stream() throws IOException {
        when(socketHandler.createServerSocket()).thenReturn(server);
        when(server.accept()).thenReturn(clientSocket);
        exampleHttpServer.handle();
        verify(clientSocket).getOutputStream();
    }

    @Test
    void write_data_to_output_stream() throws IOException {
        when(socketHandler.createServerSocket()).thenReturn(server);
        when(server.accept()).thenReturn(clientSocket);

        OutputStream helloStream = new ByteArrayOutputStream();
        when(clientSocket.getOutputStream()).thenReturn(helloStream);
        exampleHttpServer.handle();

        assertEquals("hello\n", helloStream.toString());
    }
}
