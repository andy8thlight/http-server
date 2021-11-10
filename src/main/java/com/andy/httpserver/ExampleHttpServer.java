package com.andy.httpserver;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ExampleHttpServer {
    private SocketHandler socketHandler;
    private ServerSocket server;

    public ExampleHttpServer(SocketHandler socketHandler, ServerSocket server) {
        this.socketHandler = socketHandler;
        this.server = server;
    }

    public void handle() throws IOException {
        server.accept();


        Socket clientSocket = socketHandler.connectToClient();

        if (clientSocket != null) {
            OutputStream outputStream = clientSocket.getOutputStream();

            if (outputStream != null) {
                String text = "hello\n";
                outputStream.write(text.getBytes(StandardCharsets.UTF_8));

            }

        }
    }
}
