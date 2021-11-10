package com.andy.httpserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketHandler {
    public static ServerSocket createServer(int portNumber) throws IOException {
        return new ServerSocket(portNumber);
    }

    public static Socket createClient(ServerSocket server) throws IOException {
        return server.accept();
    }

    public Socket connectToClient() {
        return null;
    }

    public ServerSocket createServerSocket(int portNumber) {
        return null;
    }
}
