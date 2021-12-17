package com.andy.httpserver;

import java.util.HashMap;
import java.util.Map;

public class Routes {
    final Map<String, Route> routes = new HashMap<>();

    public void addRoute(String uri, Route route) {
        this.routes.put(uri, route);
    }

    public Route getRoute(TheRequest request) {
        return routes.get(request.getPath());
    }

}