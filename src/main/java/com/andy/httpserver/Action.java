package com.andy.httpserver;

public interface Action {

    HttpResponse perform(HttpRequest request);
}
