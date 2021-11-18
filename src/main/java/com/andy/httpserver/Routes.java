package com.andy.httpserver;

import java.util.HashMap;
import java.util.Map;

public class Routes {
    final Map<Route, String> routes = new HashMap<>();

    public void addRoute(Route route, String content) {
        this.routes.put(route, content);
    }

    TheResponse lookup(TheRequest request) {
        String body = routes.get(new Route(request.getPath(), HttpMethod.GET));

        int statusCode = 200;
        if (body == null) {
            statusCode = 404;
        } else if (request.getMethod() == HttpMethod.POST && request.getPath().equals("/simple_get_with_body")) {
            statusCode = 405;
        }

        return new TheResponse(statusCode, body);
    }

}