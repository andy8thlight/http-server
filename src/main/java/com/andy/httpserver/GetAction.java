package com.andy.httpserver;

public class GetAction implements Action {
    private final String body;

    public GetAction(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public HttpResponse perform(HttpRequest request) {
        String body = getBody();
        return new HttpResponse(HttpStatus.OK, body);
    }
}
