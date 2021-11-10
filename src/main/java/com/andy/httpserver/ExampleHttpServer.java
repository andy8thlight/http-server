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

    public void handle() {
        try (
                ServerSocket serverSocket = socketHandler.createServerSocket(portNumber);
                Socket clientSocket = serverSocket.accept();
                OutputStream outputStream = clientSocket.getOutputStream();
                InputStream inputStream = clientSocket.getInputStream()
        ) {
            processRequests(inputStream, outputStream);
        } catch (IOException e) {
            throw new HttpSocketCreationException("Failed to create server socket");
        }
    }

    private void processRequests(InputStream inputStream, OutputStream outputStream) throws IOException {
        if (inputStream != null) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String text;
            while ((text = bufferedReader.readLine()) != null) {
                outputStream.write(text.getBytes(StandardCharsets.UTF_8));
            }
        }
    }

    public static void main(String[] args) {
        SocketHandler socketHandler = new SocketHandler();
        ExampleHttpServer server = new ExampleHttpServer(socketHandler, 4444);
        server.handle();
    }
}
