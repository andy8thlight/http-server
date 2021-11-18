package com.andy.httpserver;

import java.util.HashMap;
import java.util.Map;

public class Routes {
    final Map<String, Route> routes = new HashMap<>();

    public void addRoute(String uri, Route route) {
        this.routes.put(uri, route);
    }

    TheResponse lookup(TheRequest request) {
        Route route = routes.get(request.getPath());

        int statusCode = 200;
        String body = "";
        if (route == null) {
            return new TheResponse(404, "");
        }

        if (request.getMethod() != HttpMethod.HEAD) {
            if (request.getMethod() != route.getHttpMethod()) {
                return new TheResponse(405, body);
            }
        }

        body = route.getBody();
        return new TheResponse(statusCode, body);
    }

}