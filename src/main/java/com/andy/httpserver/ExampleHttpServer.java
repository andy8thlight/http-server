package com.andy.httpserver;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ExampleHttpServer {
    private ServerSocket server;
    private SocketHandler socketHandler;

    public ExampleHttpServer(ServerSocket server, SocketHandler socketHandler) {
        this.server = server;
        this.socketHandler = socketHandler;
    }

    public void handle() throws IOException {
        socketHandler.createServerSocket();

        Socket clientSocket = server.accept();

        if (clientSocket != null) {
            OutputStream outputStream = clientSocket.getOutputStream();

            if (outputStream != null) {
                String text = "hello\n";
                outputStream.write(text.getBytes(StandardCharsets.UTF_8));

            }

        }
    }
}
