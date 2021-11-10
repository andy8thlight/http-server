package com.andy.httpserver;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class StreamHelper {
    static ByteArrayInputStream createInputStream(String data) {
        return new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
    }

}
