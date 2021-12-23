package com.andy.httpserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExampleHttpServerShould {

    @Mock
    private Server server;

    private ExampleHttpServer httpServer;

    @BeforeEach
    void setup() {
        httpServer = new ExampleHttpServer(server);
    }

    @Test
    void start_up() {
        httpServer.start();

        verify(server).createServer();
    }

    @Test
    void accept_1_request() {
        when(server.allowRequests()).thenReturn(true, false);

        httpServer.start();

        verify(server).createServer();
        verify(server, times(1)).acceptRequest();
    }

    @Test
    void accept_multiple_request() {
        when(server.allowRequests()).thenReturn(true, true, false);

        httpServer.start();

        verify(server).createServer();
        verify(server, times(2)).acceptRequest();
    }

}