package com.andy.httpserver;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ExampleHttpServerShould {

    @Mock
    private Server server;

    @Test
    void start_up() {
        ExampleHttpServer httpServer = new ExampleHttpServer(server);
        httpServer.start();

        verify(server).createServer();
    }

    @Test
    void accept_requests() {
        ExampleHttpServer httpServer = new ExampleHttpServer(server);
        httpServer.start();

        verify(server).createServer();

    }

}