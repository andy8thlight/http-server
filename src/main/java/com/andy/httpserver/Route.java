package com.andy.httpserver;

public class Route {
    private final HttpMethod httpMethod;
    private final Action action;

    public Route(HttpMethod httpMethod, Action action) {
        this.httpMethod = httpMethod;
        this.action = action;
    }

    public String getBody() {
        if (action instanceof SimpleBodyAction) {
            SimpleBodyAction bodyAction = (SimpleBodyAction) action;
            return bodyAction.getBody();
        }
        return null;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public Action getAction() {
        return action;
    }
}
