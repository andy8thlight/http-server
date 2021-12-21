package com.andy.httpserver;

import static java.util.Arrays.stream;

public enum HttpMethod {
    POST(), HEAD(), GET(), OPTIONS(), PUT();

    static boolean isHttpVerb(String line) {
        return stream(values()).anyMatch(value -> line.startsWith(value.name()));
    }

}
