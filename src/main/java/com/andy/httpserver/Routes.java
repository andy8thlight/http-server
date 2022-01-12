package com.andy.httpserver;

import java.util.*;

class Routes {
    final Map<String, Methods> routes = new HashMap<>();

    void addRoute(String uri, Route route) {
        routes.computeIfAbsent(uri, ignore -> new Methods()).add(route);
    }

    HttpResponse process(HttpRequest request) {
        Methods methods = routes.get(request.getPath());

        Content content = new Content("", ContentType.TEXT_PLAIN);
        if (methods == null) {
            return new HttpResponse(HttpStatus.NOT_FOUND, content);
        }

        if (request.getMethod() == HttpMethod.HEAD) {
            return new HttpResponse(HttpStatus.OK, content);
        }

        if (request.getMethod() == HttpMethod.OPTIONS) {
            HttpResponse response = new HttpResponse(HttpStatus.OK, content);
            response.addHeader("Allow", methods.toString());
            return response;
        }

        if (methods.hasMethods(request.getMethod())) {
            HttpResponse response = new HttpResponse(HttpStatus.NOT_ALLOWED, content);
            response.addHeader("Allow", methods.toString());
            return response;
        }

        Route route = methods.findRoute(request);
        Action action = route.getAction();
        return action.perform(request);
    }
}
