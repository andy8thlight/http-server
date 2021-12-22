package com.andy.httpserver;

public class RedirectAction implements Action {
    private final String url;

    public RedirectAction(String url) {
        this.url = url;
    }

    public HttpResponse perform() {
        HttpResponse httpResponse = new HttpResponse(HttpStatus.MOVED_PERMANENTLY, "");
        httpResponse.addHeader("Location", url);
        return httpResponse;
    }
}
