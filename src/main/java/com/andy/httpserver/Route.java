package com.andy.httpserver;

import java.util.Objects;

public class Route {
    private String uri;
    private HttpMethod httpMethod;

    public Route(String uri, HttpMethod httpMethod) {
        this.uri = uri;
        this.httpMethod = httpMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return Objects.equals(uri, route.uri) && httpMethod == route.httpMethod;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uri, httpMethod);
    }
}
