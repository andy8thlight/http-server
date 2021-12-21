package com.andy.httpserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class Methods {
    List<Route> routeList = new ArrayList<>();

    public void add(Route route) {
        routeList.add(route);
    }

    @Override
    public String toString() {
        List<HttpMethod> httpMethods = getHttpMethods();
        StringBuilder output = new StringBuilder();
        for (HttpMethod method : httpMethods) {
            output.append(method.name()).append(", ");
        }

        Optional<HttpMethod> first = httpMethods.stream().filter(method -> method == HttpMethod.HEAD).findFirst();
        if (first.isEmpty()) {
            output.append("HEAD, ");
        }

        output.append("OPTIONS");
        return output.toString();
    }

    boolean hasMethods(HttpMethod method) {
        List<HttpMethod> methods = getHttpMethods();
        List<HttpMethod> availableVerbs = methods.stream().filter(verb -> verb == method).collect(Collectors.toList());
        return availableVerbs.isEmpty();
    }

    List<HttpMethod> getHttpMethods() {
        return routeList.stream().map(Route::getHttpMethod).collect(Collectors.toList());
    }

    List<Route> getRoutes() {
        return routeList;
    }

    Route findRoute(HttpRequest request) {
        Optional<Route> first = getRoutes().stream().filter(verb -> verb.getHttpMethod() == request.getMethod()).findFirst();
        return first.get();
    }
}
