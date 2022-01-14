package com.andy.httpserver;

public class GetAction implements Action {
    private final Content content;

    public GetAction(Content content) {
        this.content = content;
    }

    public HttpResponse perform(HttpRequest request) {
        HttpResponse httpResponse = new HttpResponse(HttpStatus.OK, content);


        /*
        content.getBody();
        httpResponse.setBody("");
        */


        ContentType contentType = content.getContentType();
        httpResponse.addHeader("Content-Type", contentType.toString());
        return httpResponse;
    }
}
