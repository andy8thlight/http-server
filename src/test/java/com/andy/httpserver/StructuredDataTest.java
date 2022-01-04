package com.andy.httpserver;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(HttpServerSetupExtension.class)
public class StructuredDataTest {

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
