package com.andy.httpserver;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static io.restassured.RestAssured.get;

public class SimpleGetTest {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost:5555";

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            SocketHandler socketHandler = new SocketHandler();
            ExampleHttpServer httpServer = new ExampleHttpServer(socketHandler, 5555);
            httpServer.handle();
        });
    }

    @Test
    void should_get_200_ok() {
        get("/").then().statusCode(200);
    }
}
