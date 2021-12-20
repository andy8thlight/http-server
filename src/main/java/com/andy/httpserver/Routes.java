package com.andy.httpserver;

import java.util.HashMap;
import java.util.Map;

public class Routes {
    final Map<String, Route> routes = new HashMap<>();

    public void addRoute(String uri, Route route) {
        this.routes.put(uri, route);
    }

    public HttpResponse process(HttpRequest request) {
        Route route = getRoute(request);
        HttpResponse httpResponse;
        if (route == null) {
            httpResponse = new HttpResponse(HttpStatus.NOT_FOUND, "");
        } else if (request.getMethod() == HttpMethod.OPTIONS) {
            HttpResponse response = new HttpResponse(HttpStatus.OK, route.getBody());
            response.setHeader("Allow", route.getAllowHeader());
            httpResponse = response;
        } else if (request.getMethod() == HttpMethod.HEAD) {
            httpResponse = new HttpResponse(HttpStatus.OK, "");
        } else if (request.getMethod() != route.getHttpMethod()) {
            HttpResponse response = new HttpResponse(HttpStatus.NOT_ALLOWED, "");
            response.setHeader("Allow", route.getAllowHeader());
            httpResponse = response;
        } else if (request.getMethod() == HttpMethod.POST) {
            // TODO: Special handling here
            HttpResponse response = new HttpResponse(HttpStatus.OK, "");
            String requestBody = request.getBody();
            response.setBody(requestBody);
            httpResponse = response;
        } else {
            httpResponse = new HttpResponse(HttpStatus.OK, route.getBody());
        }

        return httpResponse;
    }

    public Route getRoute(HttpRequest request) {
        return routes.get(request.getPath());
    }

}