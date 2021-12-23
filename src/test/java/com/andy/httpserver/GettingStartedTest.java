package com.andy.httpserver;

import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.andy.httpserver.HttpMethod.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.equalTo;

public class GettingStartedTest {

    static final int PORT_NUMBER = 5555;
    static ExecutorService executorService;

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost:" + PORT_NUMBER;
        executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> ExampleHttpServer.startHttpServer(PORT_NUMBER, createTestRoutes()));
    }

    @AfterAll
    static void tearDown() {
        executorService.shutdown();
    }

    static private Routes createTestRoutes() {
        Routes routes = new Routes();
        routes.addRoute("/simple_get_with_body", new Route(GET, new GetAction("Hello world\n")));
        routes.addRoute("/simple_get", new Route(GET, new GetAction("")));
        routes.addRoute("/simple_get_2", new Route(GET, new GetAction("")));
        routes.addRoute("/echo_body", new Route(POST, new PostAction()));
        routes.addRoute("/head_request", new Route(HEAD, new GetAction("")));
        routes.addRoute("/method_options", new Route(GET, new GetAction("")));
        routes.addRoute("/method_options2", new Route(GET, new GetAction("")));
        routes.addRoute("/method_options2", new Route(PUT, new GetAction("")));
        routes.addRoute("/method_options2", new Route(POST, new GetAction("")));
        routes.addRoute("/redirect", new Route(GET, new RedirectAction("http://0.0.0.0:5000/simple_get")));
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
    void posting_echos_the_body() {
        given().
            body("some text\n").
        when().
            post("/echo_body").
        then().
            statusCode(200).
            body(equalTo("some text"));
    }

    @Test
    void method_options() {
        given().
            options("/method_options").
        then().
            statusCode(200).
            header("Allow", equalTo("GET, HEAD, OPTIONS")).
            body(emptyString());
    }

    @Test
    void method_options_for_mutliple_verbs() {
        given().
            options("/method_options2").
        then().
            statusCode(200).
            header("Allow", equalTo("GET, PUT, POST, HEAD, OPTIONS")).
            body(emptyString());
    }

    @Test
    void method_not_allowed() {
        given().
            get("/head_request").
        then().
            statusCode(405).
            header("Allow", equalTo("HEAD, OPTIONS")).
            body(emptyString());
    }

    @Test
    void resource_moved() {
        given().
        when().
            redirects().follow(false).
            get("/redirect").
        then().
            statusCode(301).
            header("Location", equalTo("http://0.0.0.0:5000/simple_get")).
            body(emptyString());
    }
}