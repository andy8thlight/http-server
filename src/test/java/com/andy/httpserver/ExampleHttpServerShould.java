package com.andy.httpserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

        verify(server).createServerSocket();
    }

    @Test
    void accept_1_request() {
        when(server.allowsRequests()).thenReturn(true, false);

        httpServer.start();

        verify(server).createServerSocket();
        verify(server, times(1)).acceptRequest();
    }

    @Test
    void accept_multiple_request() {
        when(server.allowsRequests()).thenReturn(true, true, false);

        httpServer.start();

        verify(server).createServerSocket();
        verify(server, times(2)).acceptRequest();
    }

    @Test
    void stop_server() {
        when(server.allowsRequests()).thenReturn(true, true, false);

        httpServer.start();

        verify(server).createServerSocket();
        verify(server, times(2)).acceptRequest();
        verify(server).closeServerSocket();
    }

    @Test
    void shutdown_server() {
        httpServer.shutdown();

        verify(server).disallowRequests();
    }

}