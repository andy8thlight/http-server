package com.andy.httpserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ExampleHttpServer {
    private final HttpRequestProcessor httpRequestProcessor = new HttpRequestProcessor();
    private SocketHandler socketHandler;
    private int portNumber;

    public ExampleHttpServer(SocketHandler socketHandler, int portNumber) {
        this.socketHandler = socketHandler;
        this.portNumber = portNumber;
    }

    public void handle() {
        try (ServerSocket serverSocket = socketHandler.createServerSocket(portNumber)) {
            for (;;) {
                acceptRequest(serverSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void acceptRequest(ServerSocket serverSocket) {
        try (
                Socket clientSocket = serverSocket.accept();
                OutputStream outputStream = clientSocket.getOutputStream();
                InputStream inputStream = clientSocket.getInputStream()
        ) {
            httpRequestProcessor.processRequests(inputStream, outputStream);
        } catch (IOException | BadRequestException e) {
            System.out.println("Fatal error" + e);
            throw new HttpSocketCreationException("Failed to create server socket");
        }
    }

    public static void main(String[] args) {
        SocketHandler socketHandler = new SocketHandler();
        ExampleHttpServer server = new ExampleHttpServer(socketHandler, 4444);
        server.handle();
    }
}
