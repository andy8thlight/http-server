package com.andy.httpserver;

public class ExampleHttpServer {
    final Server server;

    public ExampleHttpServer(Server server) {
        this.server = server;
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

    public void start() {
        createServer();
    }

    void createServer() {
        server.createServer();
    }

    public void handle() {
        for (;;) {
            server.acceptRequest();
        }
    }
}
