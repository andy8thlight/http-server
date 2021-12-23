package com.andy.httpserver;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> httpServer.start());
    }

    public void start() {
        server.createServer();
        while (server.allowsRequests()) {
            server.acceptRequest();
        }
        server.stop();
    }
}
