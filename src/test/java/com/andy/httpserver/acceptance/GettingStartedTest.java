package com.andy.httpserver.acceptance;

import com.andy.httpserver.ServerTestHelper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.equalTo;

public class GettingStartedTest {
    @BeforeAll
    static void setup() {
        ServerTestHelper.createServer(5555);
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
    @Disabled
    void resource_moved() {
        given().
            get("/redirect").
        then().
            statusCode(301).
            header("Location", equalTo("http://0.0.0.0:5000/simple_get")).
            body(emptyString());
    }
}
