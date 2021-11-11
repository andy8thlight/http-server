package com.andy.httpserver;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

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
    void should_get_200() {
        given().
            get("/simple_get").
        then().
            statusCode(200).
            body(equalTo(""));
    }

    @Test
    @Disabled
    void should_get_200_with_body() {
        given().
            get("/simple_get_with_body").
        then().
            statusCode(200).
            body(equalTo("Hello world"));
    }
}
