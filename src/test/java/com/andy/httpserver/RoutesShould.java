package com.andy.httpserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoutesShould {

    private Routes routes;

    @BeforeEach
    void setup() {
        routes = new Routes();
        routes.addRoute("/route1", new Route(HttpMethod.GET, new GetAction("body1")));
        routes.addRoute("/route1", new Route(HttpMethod.POST, new GetAction("body2")));
        routes.addRoute("/head_request", new Route(HttpMethod.HEAD, new GetAction("")));
        routes.addRoute("/redirect", new Route(HttpMethod.GET, new RedirectAction("http://0.0.0.0:5000/simple_get")));
        routes.addRoute("/redirect2", new Route(HttpMethod.GET, new RedirectAction("http://0.0.0.0:5000/somewhere_else")));

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
    void respond_to_head_request() {
        HttpResponse httpResponse = routes.process(new HttpRequest(HttpMethod.HEAD, "localhost", "/head_request", ""));
        assertEquals(HttpStatus.OK, httpResponse.getStatus());
    }

    @Test
    void send_redirect() {
        HttpResponse httpResponse = routes.process(new HttpRequest(HttpMethod.GET, "localhost", "/redirect", ""));
        assertEquals(HttpStatus.MOVED_PERMANENTLY, httpResponse.getStatus());
        assertEquals("http://0.0.0.0:5000/simple_get", httpResponse.getHeader("Location"));
    }

    @Test
    void send_another_redirect() {
        HttpResponse httpResponse = routes.process(new HttpRequest(HttpMethod.GET, "localhost", "/redirect2", ""));
        assertEquals(HttpStatus.MOVED_PERMANENTLY, httpResponse.getStatus());
        assertEquals("http://0.0.0.0:5000/somewhere_else", httpResponse.getHeader("Location"));
    }

}
