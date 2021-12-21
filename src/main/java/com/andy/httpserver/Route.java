package com.andy.httpserver;

class Route {
    private final HttpMethod httpMethod;
    private final Action action;

    Route(HttpMethod httpMethod, Action action) {
        this.httpMethod = httpMethod;
        this.action = action;
    }

    HttpMethod getHttpMethod() {
        return httpMethod;
    }

    Action getAction() {
        return action;
    }
}
