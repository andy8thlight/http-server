package com.andy.httpserver;

public class ExampleHttpServer {
    private SocketHandler socketHandler;

    public ExampleHttpServer(SocketHandler socketHandler) {
        this.socketHandler = socketHandler;
    }

    public void handle() {
        socketHandler.connectToClient();
    }
}
