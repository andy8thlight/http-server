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

        if (route == null) {
            return new TheResponse(404, "", HttpStatus.NOT_FOUND);
        }

        if (request.getMethod() != HttpMethod.HEAD) {
            if (request.getMethod() != route.getHttpMethod()) {
                return new TheResponse(405, "", HttpStatus.NOT_ALLOWED);
            }
        }

        return new TheResponse(200, route.getBody(), HttpStatus.OK);
    }

}