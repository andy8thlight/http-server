package com.andy.httpserver;

public class GetAction implements Action {
    private final String body;

    public GetAction(Content content) {
        this.body = content.getBody();
    }

    public HttpResponse perform(HttpRequest request) {
        return new HttpResponse(HttpStatus.OK, body);
    }
}
