package com.andy.httpserver;

import io.restassured.RestAssured;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.andy.httpserver.HttpMethod.*;

public class ServerTestHelper {
    public static void createServer(int portNumber) {
        RestAssured.baseURI = "http://localhost:" + portNumber;

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> ExampleHttpServer.startHttpServer(portNumber, createTestRoutes()));
    }

    private static Routes createTestRoutes() {
        Routes routes = new Routes();
        routes.addRoute("/simple_get_with_body", new Route(GET, new SimpleBodyAction("Hello world\n")));
        routes.addRoute("/simple_get", new Route(GET, new SimpleBodyAction("")));
        routes.addRoute("/simple_get_2", new Route(GET, new SimpleBodyAction("")));
        routes.addRoute("/echo_body", new Route(POST, new SimpleBodyAction("")));
        routes.addRoute("/head_request", new Route(HEAD, new SimpleBodyAction("")));
        routes.addRoute("/method_options", new Route(GET, new SimpleBodyAction("")));
        routes.addRoute("/method_options2", new Route(GET, new SimpleBodyAction("")));
        routes.addRoute("/method_options2", new Route(PUT, new SimpleBodyAction("")));
        routes.addRoute("/method_options2", new Route(POST, new SimpleBodyAction("")));
        routes.addRoute("/redirect", new Route(GET, new RedirectAction("http://0.0.0.0:5000/simple_get")));
        return routes;
    }
}
