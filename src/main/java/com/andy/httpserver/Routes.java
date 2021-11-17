package com.andy.httpserver;

import java.util.HashMap;
import java.util.Map;

public class Routes {
    final Map<String, String> routes = new HashMap<>();

    public void addRoute(String uri, String content) {
        this.routes.put(uri, content);
    }

    public String lookup(String route) {
        return routes.get(route);
    }
}