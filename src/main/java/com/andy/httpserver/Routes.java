package com.andy.httpserver;

import java.util.HashMap;
import java.util.Map;

public class Routes {
    final Map<String, String> routes = createRoutes();

    public Map<String, String> createRoutes() {
        Map<String, String> routes = new HashMap<>();
        routes.put("/simple_get_with_body", "Hello world\n");
        routes.put("/simple_get", "");
        routes.put("/simple_get_2", "");
        routes.put("/", "");
        return routes;
    }

    public String lookup(String route) {
        return routes.get(route);
    }
}