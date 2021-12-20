package com.andy.httpserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Routes {
    final Map<String, List<Route>> routes = new HashMap<>();

    public void addRoute(String uri, Route route) {
        List<Route> verbs;
        if (!routes.containsKey(uri)) {
            verbs = new ArrayList<>();
        } else {
            verbs = this.routes.get(uri);
        }

        verbs.add(route);
        routes.put(uri, verbs);
    }

    public HttpResponse process(HttpRequest request) {
        List<Route> verbs = routes.get(request.getPath());

        if (verbs == null) {
            return new HttpResponse(HttpStatus.NOT_FOUND, "");
        }

        Route route = verbs.get(0);
        if (request.getMethod() == HttpMethod.OPTIONS) {
            HttpResponse response = new HttpResponse(HttpStatus.OK, route.getBody());
            response.setHeader("Allow", route.getAllowHeader());
            return response;
        }

        if (request.getMethod() == HttpMethod.HEAD) {
            return new HttpResponse(HttpStatus.OK, "");
        }

        if (request.getMethod() != route.getHttpMethod()) {
            HttpResponse response = new HttpResponse(HttpStatus.NOT_ALLOWED, "");
            response.setHeader("Allow", route.getAllowHeader());
            return response;
        }

        if (request.getMethod() == HttpMethod.POST) {
            // TODO: Special handling here
            HttpResponse response = new HttpResponse(HttpStatus.OK, "");
            String requestBody = request.getBody();
            response.setBody(requestBody);
            return response;
        }

        return new HttpResponse(HttpStatus.OK, route.getBody());
    }

}
