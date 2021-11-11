package com.andy.httpserver;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class RequestProcessor {
    public RequestProcessor() {
    }

    void processRequests(InputStream inputStream, OutputStream outputStream) throws IOException, BadRequestException {
        if (inputStream != null) {
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            RequestParser requestParser = new RequestParser();

            TheRequest theRequest = requestParser.parseRequest(inputStream);

            String response = "HTTP/1.1 200 OK\n";
            outputStream.write(response.getBytes(StandardCharsets.UTF_8));

//            String text;
//            while ((text = bufferedReader.readLine()) != null) {

//
//                outputStream.write(text.getBytes(StandardCharsets.UTF_8));
//            }
        }
    }
}