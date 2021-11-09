package com.andy.httpserver;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SocketHandlerShould {

    @Test
    void create_a_server_socket() throws IOException {
        ServerSocket server = SocketHandler.create(8888);
        assertNotNull(server);
    }

}
