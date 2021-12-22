package com.andy.httpserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ExampleHttpServer {
    private final HttpRequestProcessor httpRequestProcessor;
    private final SocketHandler socketHandler;
    private final int portNumber;
    ServerSocket serverSocket = null;

    public ExampleHttpServer(SocketHandler socketHandler, int portNumber, HttpRequestProcessor httpRequestProcessor) {
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
        Routes routes = new Routes();
        routes.addRoute("/simple_get", new Route(HttpMethod.GET, new GetAction("Howdee")));
        routes.addRoute("/simple_post", new Route(HttpMethod.POST, new GetAction("Howdee")));
        routes.addRoute("/redirect", new Route(HttpMethod.GET, new RedirectAction("http://localhost:4444/simple_get")));
        startHttpServer(4444, routes);
    }

    public static void startHttpServer(int portNumber, Routes routes) {
        SocketHandler socketHandler = new SocketHandler();
        HttpRequestProcessor httpRequestProcessor = new HttpRequestProcessor(routes);
        ExampleHttpServer server = new ExampleHttpServer(socketHandler, portNumber, httpRequestProcessor);
        server.createServer();
        server.handle();
    }
}
