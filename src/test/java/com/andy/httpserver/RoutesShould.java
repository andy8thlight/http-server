package com.andy.httpserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoutesShould {

    private Routes routes;

    @BeforeEach
    void setup() {
        routes = new Routes();
        routes.addRoute("/route1", new Route(HttpMethod.GET, "body1"));
        routes.addRoute("/route1", new Route(HttpMethod.POST, "body2"));
        routes.addRoute("/head_request", new Route(HttpMethod.HEAD, ""));

    }

    @Test
    void find_route() {
        HttpResponse httpResponse = routes.process(new HttpRequest(HttpMethod.GET, "localhost", "/route1", ""));
        assertEquals(HttpStatus.OK, httpResponse.getStatus());
    }

    @Test
    void not_find_route() {
        HttpResponse httpResponse = routes.process(new HttpRequest(HttpMethod.POST, "localhost", "/doesntexist", ""));
        assertEquals(HttpStatus.NOT_FOUND, httpResponse.getStatus());
    }

    @Test
    void give_method_not_allowed() {
        HttpResponse httpResponse = routes.process(new HttpRequest(HttpMethod.PUT, "localhost", "/route1", ""));
        assertEquals(HttpStatus.NOT_ALLOWED, httpResponse.getStatus());
    }

    @Test
    void select_get_route_when_multiple_methods() {
        HttpResponse httpResponse = routes.process(new HttpRequest(HttpMethod.GET, "localhost", "/route1", ""));
        assertEquals(HttpStatus.OK, httpResponse.getStatus());
        assertEquals("body1", httpResponse.getBody());
    }

    @Test
    void select_post_route_when_multiple_methods() {
        HttpResponse httpResponse = routes.process(new HttpRequest(HttpMethod.POST, "localhost", "/route1", ""));
        assertEquals(HttpStatus.OK, httpResponse.getStatus());
    }

    @Test
    void repond_to_head_request() {
        HttpResponse httpResponse = routes.process(new HttpRequest(HttpMethod.HEAD, "localhost", "/head_request", ""));
        assertEquals(HttpStatus.OK, httpResponse.getStatus());
    }


}
