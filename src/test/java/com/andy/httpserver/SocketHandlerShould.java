package com.andy.httpserver;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SocketHandlerShould {

    private ServerSocket server;

    @AfterEach
    void tearDown() throws IOException {
        server.close();
    }

    @Test
    void create_a_server_socket() throws IOException {
        server = SocketHandler.createServer(8888);
        assertNotNull(server);
    }

    @Test
    @Disabled
    void open_a_client_socket() throws IOException {
        ServerSocket server = SocketHandler.createServer(8888);
        Socket client = SocketHandler.createClient(server);
        assertNotNull(client);
    }


}
