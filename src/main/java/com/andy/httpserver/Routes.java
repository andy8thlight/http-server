package com.andy.httpserver;

import java.util.*;

public class Routes {
    final Map<String, Methods> routes = new HashMap<>();

    public void addRoute(String uri, Route route) {
        routes.computeIfAbsent(uri, ignore -> new Methods()).add(route);
    }

    public HttpResponse process(HttpRequest request) {
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
            HttpResponse response = new HttpResponse(HttpStatus.OK, "");
            String requestBody = request.getBody();
            response.setBody(requestBody);
            return response;
        }

        Route route = verbs.findRoute(request);

        // TODO: Maybe some ploymorphism here.....
        Action action = route.getAction();
        if (action instanceof RediectAction) {
            RediectAction rediectAction = (RediectAction) action;
            HttpResponse httpResponse = new HttpResponse(HttpStatus.MOVED_PERMANENTLY, "");
            httpResponse.addHeader("Location", rediectAction.getLocation());
            return httpResponse;
        }

        if (action instanceof SimpleBodyAction) {
            SimpleBodyAction bodyAction = (SimpleBodyAction) action;
            String body = bodyAction.getBody();
            return new HttpResponse(HttpStatus.OK, body);
        }

        return new HttpResponse(HttpStatus.OK, "");
    }
}
