package com.andy.httpserver;

public class PostAction implements Action {
    @Override
    public HttpResponse perform(HttpRequest request) {
        HttpResponse response = new HttpResponse(HttpStatus.OK, "");
        String body = request.getBody();
        response.setBody(body);
        return response;
    }
}
