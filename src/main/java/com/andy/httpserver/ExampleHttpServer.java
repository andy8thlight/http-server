package com.andy.httpserver;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ExampleHttpServer {
    private SocketHandler socketHandler;

    public ExampleHttpServer(SocketHandler socketHandler) {
        this.socketHandler = socketHandler;
    }

    public void handle() throws IOException {
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
