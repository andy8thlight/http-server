package com.andy.httpserver;

import java.io.IOException;
import java.net.ServerSocket;

public class SocketHandler {
    public static ServerSocket create(int portNumber) throws IOException {
        return new ServerSocket(portNumber);
    }
}
