package com.andy.httpserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SocketHandlerShould {

    @Mock
    private ServerSocket server;
    @Mock
    private Socket clientSocket;

    @Test
    void create_a_server_socket() throws IOException {
        ServerSocket server = SocketHandler.createServer(8888);
        assertNotNull(server);
        int localPort = server.getLocalPort();
        assertEquals(8888, localPort);
        server.close();
    }

    @Test
    void open_a_client_socket() throws IOException {
        when(server.accept()).thenReturn(clientSocket);
        Socket client = SocketHandler.createClient(server);
        assertNotNull(client);
    }
}
