package com.andy.httpserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoutesShould {

    public HttpHeaders getHeaders;

    private Routes routes;

    @BeforeEach
    void setup() {
        routes = new Routes();
        routes.addRoute("/route1", new Route(HttpMethod.GET, new GetAction("body1")));
        routes.addRoute("/route1", new Route(HttpMethod.POST, new PostAction()));
        routes.addRoute("/head_request", new Route(HttpMethod.HEAD, new GetAction("")));
        routes.addRoute("/redirect", new Route(HttpMethod.GET, new RedirectAction("http://0.0.0.0:5000/simple_get")));
        routes.addRoute("/redirect2", new Route(HttpMethod.GET, new RedirectAction("http://0.0.0.0:5000/somewhere_else")));
        getHeaders = new HttpHeaders();
        getHeaders.add("Host", "localhost");
    }

    @Test
    void find_route() {
        HttpResponse httpResponse = routes.process(new HttpRequest(HttpMethod.GET, "/route1", getHeaders, ""));
        assertEquals(HttpStatus.OK, httpResponse.getStatus());
    }

    @Test
    void not_find_route() {
        HttpResponse httpResponse = routes.process(new HttpRequest(HttpMethod.POST, "/doesntexist", getHeaders, ""));
        assertEquals(HttpStatus.NOT_FOUND, httpResponse.getStatus());
    }

    @Test
    void give_method_not_allowed() {
        HttpResponse httpResponse = routes.process(new HttpRequest(HttpMethod.PUT, "/route1", getHeaders, ""));
        assertEquals(HttpStatus.NOT_ALLOWED, httpResponse.getStatus());
    }

    @Test
    void select_get_route_when_multiple_methods() {
        HttpResponse httpResponse = routes.process(new HttpRequest(HttpMethod.GET, "/route1", getHeaders, ""));
        assertEquals(HttpStatus.OK, httpResponse.getStatus());
        assertEquals("body1", httpResponse.getBody());
    }

    @Test
    void respond_to_head_request() {
        HttpResponse httpResponse = routes.process(new HttpRequest(HttpMethod.HEAD, "/head_request", getHeaders, ""));
        assertEquals(HttpStatus.OK, httpResponse.getStatus());
    }

    @Test
    void send_redirect() {
        HttpResponse httpResponse = routes.process(new HttpRequest(HttpMethod.GET, "/redirect", getHeaders, ""));
        assertEquals(HttpStatus.MOVED_PERMANENTLY, httpResponse.getStatus());
        assertEquals("http://0.0.0.0:5000/simple_get", httpResponse.getHeader("Location"));
    }

    @Test
    void send_another_redirect() {
        HttpResponse httpResponse = routes.process(new HttpRequest(HttpMethod.GET, "/redirect2", getHeaders, ""));
        assertEquals(HttpStatus.MOVED_PERMANENTLY, httpResponse.getStatus());
        assertEquals("http://0.0.0.0:5000/somewhere_else", httpResponse.getHeader("Location"));
    }

    @Test
    void select_post_route_when_multiple_methods() {
        HttpResponse httpResponse = routes.process(new HttpRequest(HttpMethod.POST, "/route1", getHeaders, ""));
        assertEquals(HttpStatus.OK, httpResponse.getStatus());
    }

    @Test
    void ignore_request_body_when_no_content_length_header() {
        HttpHeaders postHeaders = new HttpHeaders();
        postHeaders.add("Host", "localhost");
        HttpResponse httpResponse = routes.process(new HttpRequest(HttpMethod.POST, "/route1", postHeaders, "blah"));
        assertEquals(HttpStatus.OK, httpResponse.getStatus());
        assertEquals("", httpResponse.getBody());
    }

    @Test
    void echo_request_body_when_content_length_header_exists() {
        HttpHeaders postHeaders = new HttpHeaders();
        postHeaders.add("Host", "localhost");
        postHeaders.add("Content-Length", "4");
        HttpResponse httpResponse = routes.process(new HttpRequest(HttpMethod.POST, "/route1", postHeaders, "blah"));
        assertEquals(HttpStatus.OK, httpResponse.getStatus());
        assertEquals("blah", httpResponse.getBody());
    }

    @Test
    @Disabled("probably doesnt belong here")
    void echo_request_body_with_multiple_lines() {
        HttpHeaders postHeaders = new HttpHeaders();
        postHeaders.add("Host", "localhost");
        String blah = "blah\nblah\n";
        postHeaders.add("Content-Length", String.valueOf(blah.length()));
        HttpResponse httpResponse = routes.process(new HttpRequest(HttpMethod.POST, "/route1", postHeaders, blah));
        assertEquals(HttpStatus.OK, httpResponse.getStatus());
        assertEquals(blah, httpResponse.getBody());
    }

}
