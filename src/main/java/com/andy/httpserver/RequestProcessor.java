package com.andy.httpserver;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class RequestProcessor {
    public RequestProcessor() {
    }

    void processRequests(InputStream inputStream, OutputStream outputStream) throws IOException {
        if (inputStream != null) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String text;
            while ((text = bufferedReader.readLine()) != null) {
                outputStream.write(text.getBytes(StandardCharsets.UTF_8));
            }
        }
    }
}