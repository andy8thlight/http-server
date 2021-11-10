package com.andy.httpserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ExampleHttpServer {
    private SocketHandler socketHandler;
    private int portNumber;

    public ExampleHttpServer(SocketHandler socketHandler, int portNumber) {
        this.socketHandler = socketHandler;
        this.portNumber = portNumber;
    }

    public void handle() throws IOException {
        OutputStream outputStream = null;
        InputStream inputStream = null;
        ServerSocket serverSocket = socketHandler.createServerSocket(portNumber);
        if (serverSocket != null) {
            Socket clientSocket = serverSocket.accept();
            if (clientSocket != null) {
                outputStream = clientSocket.getOutputStream();
                inputStream = clientSocket.getInputStream();
            }
        }
        if (inputStream == null) return;
        if (outputStream == null) return;


        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String text;
        while ((text = bufferedReader.readLine()) != null) {
            outputStream.write(text.getBytes(StandardCharsets.UTF_8));
        }
    }
}
