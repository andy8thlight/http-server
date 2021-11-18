package com.andy.httpserver;

import java.util.HashMap;
import java.util.Map;

public class Routes {
    final Map<String, String> routes = new HashMap<>();

    public void addRoute(String uri, String content) {
        this.routes.put(uri, content);
    }

    TheResponse lookup(TheRequest request) {
        String body = lookup(request.getPath());

        int statusCode = 200;
        if (body == null) {
            statusCode = 404;
        } else if (request.getVerb().equals("POST") && request.getPath().equals("/simple_get_with_body")) {
            statusCode = 405;
        }

        return new TheResponse(statusCode, body);
    }

    private String lookup(String route) {
        return routes.get(route);
    }
}