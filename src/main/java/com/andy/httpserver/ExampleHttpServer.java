package com.andy.httpserver;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExampleHttpServer {
    private static ExecutorService executorService;
    private Server server;

    public ExampleHttpServer(Server server) {
        this.server = server;
    }

    public ExampleHttpServer(int portNumber, Routes routes) {
        SocketHandler socketHandler = new SocketHandler();
        HttpRequestProcessor httpRequestProcessor = new HttpRequestProcessor(routes);
        this.server = new Server(socketHandler, portNumber, httpRequestProcessor);
    }

    public static void main(String[] args) {
        Routes routes = new Routes();
        routes.addRoute("/simple_get", new Route(HttpMethod.GET, new GetAction(new Content("Howdee"))));
        routes.addRoute("/simple_post", new Route(HttpMethod.POST, new GetAction(new Content("Howdee"))));
        routes.addRoute("/redirect", new Route(HttpMethod.GET, new RedirectAction("http://localhost:4444/simple_get")));

        ExampleHttpServer httpServer = new ExampleHttpServer(4444, routes);
        httpServer.startHttpServer();
    }

    public void startHttpServer() {
        executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> start());
    }

    public void start() {
        server.createServerSocket();
        while (server.allowsRequests()) {
            server.acceptRequest();
        }
        server.closeServerSocket();
    }

    // TODO: I'm generally pretty unhappy with the shutdown stuff and need to find a better way to test this.
    public void shutdown() {
        server.disallowRequests();
        if (executorService != null) {
            executorService.shutdown();
        }
    }
}
