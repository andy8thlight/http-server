package com.andy.httpserver;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RouteShould {

    @Test
    void render_allow_headers_for_get() {
        Route route = new Route(HttpMethod.GET, "");
        String expected = route.getAllowHeader();
        assertEquals(expected, "GET, HEAD, OPTIONS");
    }

    @Test
    void render_allow_headers_for_post() {
        Route route = new Route(HttpMethod.POST, "");
        String expected = route.getAllowHeader();
        assertEquals(expected, "POST, HEAD, OPTIONS");
    }

    @Test
    void render_allow_headers_for_head() {
        Route route = new Route(HttpMethod.HEAD, "");
        String expected = route.getAllowHeader();
        assertEquals(expected, "HEAD, OPTIONS");
    }
}