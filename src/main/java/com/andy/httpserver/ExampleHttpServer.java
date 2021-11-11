package com.andy.httpserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ExampleHttpServer {
    private final HttpRequestProcessor httpRequestProcessor = new HttpRequestProcessor();
    private SocketHandler socketHandler;
    private int portNumber;
    ServerSocket serverSocket = null;

    public ExampleHttpServer(SocketHandler socketHandler, int portNumber) {
        this.socketHandler = socketHandler;
        this.portNumber = portNumber;
    }

    void createServer() {
        try {
            serverSocket = socketHandler.createServerSocket(portNumber);
        } catch (IOException e) {
            throw new HttpSocketCreationException("Failed to create server socket");
        }
    }

    public void handle() {
        for (;;) {
            acceptRequest(serverSocket);
        }
    }

    void acceptRequest(ServerSocket serverSocket) {
        try (
                Socket clientSocket = serverSocket.accept();
                OutputStream outputStream = clientSocket.getOutputStream();
                InputStream inputStream = clientSocket.getInputStream()
        ) {
            httpRequestProcessor.processRequests(inputStream, outputStream);
        } catch (IOException | BadRequestException e) {
            System.out.println("Fatal error" + e);
            throw new HttpSocketCreationException("Failed to establish connection to client");
        }
    }

    public static void main(String[] args) {
        startHttpServer(4444);
    }

    static void startHttpServer(int portNumber) {
        SocketHandler socketHandler = new SocketHandler();
        ExampleHttpServer server = new ExampleHttpServer(socketHandler, portNumber);
        server.createServer();
        server.handle();
    }
}
