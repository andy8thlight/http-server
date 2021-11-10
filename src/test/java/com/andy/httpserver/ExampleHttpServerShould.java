package com.andy.httpserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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
    void open_a_socket_to_client() throws IOException {
        exampleHttpServer.handle();
        verify(socketHandler).connectToClient();
    }

    @Test
    void get_output_stream_to_write_data() throws IOException {
        when(socketHandler.connectToClient()).thenReturn(clientSocket);
        exampleHttpServer.handle();
        verify(clientSocket).getOutputStream();
    }
}
