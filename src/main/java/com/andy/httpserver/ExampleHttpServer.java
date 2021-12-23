package com.andy.httpserver;

import java.net.ServerSocket;

public class ExampleHttpServer {
    final Server server;

    public ExampleHttpServer(Server server) {
        this.server = server;
    }

    void createServer() {
        server.createServer();
    }

    public void handle() {
        for (;;) {
            server.acceptRequest(server.serverSocket);
        }
    }

    void acceptRequest(ServerSocket serverSocket) {
        server.acceptRequest(serverSocket);
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
        Server server = new Server(socketHandler, portNumber, httpRequestProcessor);
        ExampleHttpServer httpServer = new ExampleHttpServer(server);
        httpServer.createServer();
        httpServer.handle();
    }
}
