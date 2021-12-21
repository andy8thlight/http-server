package com.andy.httpserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

interface RequestProcessor {
    void processRequests(InputStream inputStream, OutputStream outputStream) throws IOException, BadRequestException;
}
