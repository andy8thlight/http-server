package com.andy.httpserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class SocketHandlerShould {

    @Mock
    private ServerSocket server;
    @Mock
    private Socket clientSocket;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_a_server_socket() throws IOException {
        ServerSocket server = SocketHandler.createServer(8888);
        assertNotNull(server);
        server.close();
    }

    @Test
    void open_a_client_socket() throws IOException {
        when(server.accept()).thenReturn(clientSocket);
        Socket client = SocketHandler.createClient(server);
        assertNotNull(client);
    }
}
