package com.andy.httpserver;

import java.util.*;

class Routes {
    final Map<String, Methods> routes = new HashMap<>();

    void addRoute(String uri, Route route) {
        routes.computeIfAbsent(uri, ignore -> new Methods()).add(route);
    }

    HttpResponse process(HttpRequest request) {
        Methods verbs = routes.get(request.getPath());

        if (verbs == null) {
            return new HttpResponse(HttpStatus.NOT_FOUND, "");
        }

        if (request.getMethod() == HttpMethod.HEAD) {
            return new HttpResponse(HttpStatus.OK, "");
        }

        if (request.getMethod() == HttpMethod.OPTIONS) {
            HttpResponse response = new HttpResponse(HttpStatus.OK, "");
            response.addHeader("Allow", verbs.toString());
            return response;
        }

        if (verbs.hasMethods(request.getMethod())) {
            HttpResponse response = new HttpResponse(HttpStatus.NOT_ALLOWED, "");
            response.addHeader("Allow", verbs.toString());
            return response;
        }




        if (request.getMethod() == HttpMethod.POST) {
            // TODO: Special handling here
            return postActionDo(request.getBody());
        }

        Route route = verbs.findRoute(request);
        Action action = route.getAction();

        if (action instanceof RediectAction) {
            return action.perform();
        }

        if (action instanceof SimpleBodyAction) {
            return action.perform();
        }

        return new HttpResponse(HttpStatus.OK, "");
    }

    private HttpResponse postActionDo(String body) {
        HttpResponse response = new HttpResponse(HttpStatus.OK, "");
        String requestBody = body;
        response.setBody(requestBody);
        return response;
    }

}
