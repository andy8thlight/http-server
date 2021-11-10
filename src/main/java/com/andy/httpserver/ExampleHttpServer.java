package com.andy.httpserver;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ExampleHttpServer {
    private ServerSocket server;
    private SocketHandler socketHandler;

    public ExampleHttpServer(SocketHandler socketHandler) {
        this.socketHandler = socketHandler;
    }

    public void handle() throws IOException {
        OutputStream outputStream = createServer();
        if (outputStream == null) return;

        String text = "hello\n";
        outputStream.write(text.getBytes(StandardCharsets.UTF_8));
    }

    private OutputStream createServer() throws IOException {
        ServerSocket server = socketHandler.createServerSocket();
        if (server == null) {
            return null;
        }
        Socket clientSocket = server.accept();
        if (clientSocket == null) {
            return null;
        }
        return clientSocket.getOutputStream();
    }

    ByteArrayInputStream createInputStream(String data) {
        return new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
    }

}
