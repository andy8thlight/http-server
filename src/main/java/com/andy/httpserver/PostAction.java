package com.andy.httpserver;

public class PostAction implements Action {
    private Content content;

    public PostAction(Content content) {
        this.content = content;
    }

    @Override
    public HttpResponse perform(HttpRequest request) {
        HttpResponse httpResponse = new HttpResponse(HttpStatus.OK, "");
        ContentType contentType = content.getContentType();
        httpResponse.addHeader("Content-Type", contentType.toString());

        Integer contentLength = request.getContentLengthHeader();
        if (contentLength > 0) {
            String body = request.getBody();
            httpResponse.setBody(body);
        }
        return httpResponse;
    }
}
