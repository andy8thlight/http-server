package com.andy.httpserver;

import java.util.*;
import java.util.stream.Collectors;

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

        if (request.getMethod() == HttpMethod.HEAD) {
            return new HttpResponse(HttpStatus.OK, "");
        }

        if (request.getMethod() == HttpMethod.OPTIONS) {
            HttpResponse response = new HttpResponse(HttpStatus.OK, "");
            response.setHeader("Allow", verbsToHeader(verbs));
            return response;
        }


        List<Route> availableVerbs = verbs.stream().filter(verb -> verb.getHttpMethod() == request.getMethod()).collect(Collectors.toList());
        if (availableVerbs.isEmpty()) {
            HttpResponse response = new HttpResponse(HttpStatus.NOT_ALLOWED, "");
            response.setHeader("Allow", verbsToHeader(verbs));
            return response;
        }

        Optional<Route> first = verbs.stream().filter(verb -> verb.getHttpMethod() == request.getMethod()).findFirst();

        Route route = first.get();

        if (request.getMethod() == HttpMethod.POST) {
            // TODO: Special handling here
            HttpResponse response = new HttpResponse(HttpStatus.OK, "");
            String requestBody = request.getBody();
            response.setBody(requestBody);
            return response;
        }

        return new HttpResponse(HttpStatus.OK, route.getBody());
    }

    private String verbsToHeader(List<Route> verbs) {
        String output = "";
        for (Route route : verbs) {
            output += (route.getHttpMethod().name() + ", ");

        }

        Optional<Route> first = verbs.stream().filter(verb -> verb.getHttpMethod() == HttpMethod.HEAD).findFirst();
        if (first.isEmpty()) {
            output += "HEAD, ";
        }

        output += "OPTIONS";
        return output;
    }

}
