package com.andy.httpserver;

public class RediectAction implements Action {
    private final String url;

    public RediectAction(String url) {
        this.url = url;
    }

    public String getLocation() {
        return url;
    }

    public HttpResponse perform() {
        HttpResponse httpResponse = new HttpResponse(HttpStatus.MOVED_PERMANENTLY, "");
        httpResponse.addHeader("Location", getLocation());
        return httpResponse;
    }
}
