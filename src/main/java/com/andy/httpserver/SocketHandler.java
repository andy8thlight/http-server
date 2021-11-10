package com.andy.httpserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketHandler {
    public static ServerSocket createServer(int portNumber) throws IOException {
        return new SocketHandler().createServerSocket(portNumber);
    }

    public static Socket createClient(ServerSocket server) throws IOException {
        return server.accept();
    }

    public ServerSocket createServerSocket(int portNumber) throws IOException {
        return new ServerSocket(portNumber);
    }
}
