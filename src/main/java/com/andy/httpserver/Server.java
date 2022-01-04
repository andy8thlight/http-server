package com.andy.httpserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

class Server {
    private boolean allowRequests = false;
    final private HttpRequestProcessor httpRequestProcessor;
    final private SocketHandler socketHandler;
    final private int portNumber;
    private ServerSocket serverSocket = null;
    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    Server(SocketHandler socketHandler, int portNumber, HttpRequestProcessor httpRequestProcessor) {
        this.socketHandler = socketHandler;
        this.portNumber = portNumber;
        this.httpRequestProcessor = httpRequestProcessor;
//        disallowRequests();
    }

    void createServerSocket() {
        try {
            serverSocket = socketHandler.createServerSocket(portNumber);
            allowRequests = true;
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
            logger.error("Failed to establish connection to client", e);
            throw new HttpSocketCreationException("Failed to establish connection to client");
        }
    }

    boolean allowsRequests() {
        return allowRequests;
    }

    public void closeServerSocket() {
        disallowRequests();
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disallowRequests() {
        allowRequests = false;
    }
}