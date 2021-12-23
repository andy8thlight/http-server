package com.andy.httpserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

class Server {
    final private HttpRequestProcessor httpRequestProcessor;
    final private SocketHandler socketHandler;
    final private int portNumber;
    private ServerSocket serverSocket = null;

    Server(SocketHandler socketHandler, int portNumber, HttpRequestProcessor httpRequestProcessor) {
        this.socketHandler = socketHandler;
        this.portNumber = portNumber;
        this.httpRequestProcessor = httpRequestProcessor;
    }

    void createServer() {
        try {
            serverSocket = socketHandler.createServerSocket(portNumber);
        } catch (IOException e) {
            throw new HttpSocketCreationException("Failed to create server socket");
        }
    }

    void acceptRequest() {
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

    boolean allowsRequests() {
        return true;
    }

    public void stop() {

    }
}