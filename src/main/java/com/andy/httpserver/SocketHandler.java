package com.andy.httpserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class SocketHandler {
    static ServerSocket createServer(int portNumber) throws IOException {
        return new SocketHandler().createServerSocket(portNumber);
    }

    static Socket createClient(ServerSocket server) throws IOException {
        return server.accept();
    }

    ServerSocket createServerSocket(int portNumber) throws IOException {
        return new ServerSocket(portNumber);
    }
}
