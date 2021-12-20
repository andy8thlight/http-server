package com.andy.httpserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoutesShould {

    private Routes routes;

    @BeforeEach
    void setup() {
        routes = new Routes();
    }

    @Test
    void find_route() {
        routes.addRoute("/route1", new Route(HttpMethod.GET, "somefink here"));
        HttpResponse httpResponse = routes.process(new HttpRequest(HttpMethod.GET, "localhost", "/route1", ""));
        assertEquals(HttpStatus.OK, httpResponse.getStatus());
    }

    @Test
    void not_find_route() {
        routes.addRoute("/route1", new Route(HttpMethod.GET, "somefink here"));
        HttpResponse httpResponse = routes.process(new HttpRequest(HttpMethod.POST, "localhost", "/doesntexist", ""));
        assertEquals(HttpStatus.NOT_FOUND, httpResponse.getStatus());
    }

    @Test
    void give_method_not_allowed() {
        routes.addRoute("/route1", new Route(HttpMethod.GET, "somefink here"));
        HttpResponse httpResponse = routes.process(new HttpRequest(HttpMethod.POST, "localhost", "/route1", ""));
        assertEquals(HttpStatus.NOT_ALLOWED, httpResponse.getStatus());
    }

    @Test
    void select_get_route_when_multiple_methods() {
        routes.addRoute("/route1", new Route(HttpMethod.GET, "body1"));
        routes.addRoute("/route1", new Route(HttpMethod.POST, "body2"));
        HttpResponse httpResponse = routes.process(new HttpRequest(HttpMethod.GET, "localhost", "/route1", ""));
        assertEquals(HttpStatus.OK, httpResponse.getStatus());
        assertEquals("body1", httpResponse.getBody());
    }

    @Test
    void select_post_route_when_multiple_methods() {
        routes.addRoute("/route1", new Route(HttpMethod.GET, "body1"));
        routes.addRoute("/route1", new Route(HttpMethod.POST, "body2"));
        HttpResponse httpResponse = routes.process(new HttpRequest(HttpMethod.POST, "localhost", "/route1", ""));
        assertEquals(HttpStatus.OK, httpResponse.getStatus());
    }


}
