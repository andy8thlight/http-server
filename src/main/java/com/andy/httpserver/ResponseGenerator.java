package com.andy.httpserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class ResponseGenerator {
    OutputStream writeData(String data) throws IOException {
        byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.write(bytes);
        return out;
    }

}
