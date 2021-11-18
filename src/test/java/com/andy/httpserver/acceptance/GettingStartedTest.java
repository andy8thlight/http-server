package com.andy.httpserver.acceptance;

import com.andy.httpserver.ExampleHttpServer;
import com.andy.httpserver.Route;
import com.andy.httpserver.Routes;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.andy.httpserver.HttpMethod.GET;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GettingStartedTest {
    @BeforeAll
    static void setup() {
        int portNumber = 5555;
        RestAssured.baseURI = "http://localhost:" + portNumber;

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> ExampleHttpServer.startHttpServer(portNumber, createTestRoutes()));
    }

    private static Routes createTestRoutes() {
        Routes routes = new Routes();
        routes.addRoute("/simple_get_with_body", new Route("/simple_get_with_body", GET, "Hello world\n"), "Hello world\n");
        routes.addRoute("/simple_get", new Route("/simple_get", GET, ""), "");
        routes.addRoute("/simple_get_2", new Route("/simple_get_2", GET, ""), "");
        return routes;
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
    void should_get_anoter_200_when_a_different_request() {
        given().
            get("/simple_get_2").
        then().
            statusCode(200).
            body(equalTo(""));
    }

    @Test
    void should_get_200_with_body() {
        given().
            get("/simple_get_with_body").
        then().
            statusCode(200).
            body(equalTo("Hello world\n"));
    }

    @Test
    void unconfigured_resources_returns_404() {
        given().
            get("/page_that_does_not_exist").
        then().
            statusCode(404);
    }

    @Test
    void executing_head_request_to_simple_get() {
        given().
            head("/simple_get").
        then().
            statusCode(200).
            body(equalTo(""));
    }

    @Test
    void head_does_not_include_body() {
        given().
            head("/simple_get_with_body").
        then().
            statusCode(200).
            body(equalTo(""));
    }

    @Test
    @Disabled
    void posting_echos_the_body() {
        given().
            body("some text").
        when().
            post("/echo_body").
        then().
            statusCode(200).
            body(equalTo("some text"));
    }
}
