package com.andy.httpserver;

public class SimpleBodyAction implements Action {
    private final String body;

    public SimpleBodyAction(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public HttpResponse perform() {
        String body = getBody();
        return new HttpResponse(HttpStatus.OK, body);
    }
}
