package com.andy.httpserver;

import java.io.IOException;
import java.net.Socket;

public class ExampleHttpServer {
    private SocketHandler socketHandler;

    public ExampleHttpServer(SocketHandler socketHandler) {
        this.socketHandler = socketHandler;
    }

    public void handle() throws IOException {
        Socket clientSocket = socketHandler.connectToClient();

        if (clientSocket != null) {
            clientSocket.getOutputStream();
        }
    }
}
