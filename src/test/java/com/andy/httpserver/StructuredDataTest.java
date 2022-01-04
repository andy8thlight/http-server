package com.andy.httpserver;

import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static com.andy.httpserver.HttpMethod.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@Disabled
public class StructuredDataTest {

    static final int PORT_NUMBER = 5556;
    static ExampleHttpServer httpServer;

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost:" + PORT_NUMBER;
        httpServer = new ExampleHttpServer(PORT_NUMBER, createTestRoutes());
        httpServer.startHttpServer();
    }

    @AfterAll
    static void tearDown() {
        httpServer.shutdown();
    }

    static private Routes createTestRoutes() {
        Routes routes = new Routes();
        routes.addRoute("/text_response", new Route(GET, new GetAction(new Content("text response"))));
        return routes;
    }


    @Test
    void should_get_200() {
        given().
            get("/text_response").
        then().
            statusCode(200).
            body(equalTo("text response")).
            header("Content-Type", equalTo("text/plain"));
    }
}
