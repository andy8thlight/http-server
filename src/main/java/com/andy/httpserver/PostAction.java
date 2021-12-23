package com.andy.httpserver;

public class PostAction implements Action {
    @Override
    public HttpResponse perform(HttpRequest request) {
        HttpResponse response = new HttpResponse(HttpStatus.OK, "");

        Integer contentLength = request.getContentLengthHeader();
        if (contentLength > 0) {
            String body = request.getBody();
            response.setBody(body);
        }
        return response;
    }
}
