package com.andy.httpserver;

public class RedirectAction implements Action {
    private final String url;

    public RedirectAction(String url) {
        this.url = url;
    }

    public HttpResponse perform(HttpRequest request) {
        BasicContent content = new BasicContent("", ContentType.TEXT_PLAIN);
        HttpResponse httpResponse = new HttpResponse(HttpStatus.MOVED_PERMANENTLY, content);
        httpResponse.addHeader("Location", url);
        return httpResponse;
    }
}
