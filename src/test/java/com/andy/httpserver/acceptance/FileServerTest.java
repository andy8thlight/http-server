package com.andy.httpserver.acceptance;

import com.andy.httpserver.HttpServerSetupExtension;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(HttpServerSetupExtension.class)
public class FileServerTest {

    @Test
    void health_check_request() {
        given().
            header("Accept", "text/html").
            get("/health-check.html").
        then().
            statusCode(200).
            body(equalTo("<strong>Status:</strong> pass")).
            header("Content-Type", equalTo("text/html"));
    }
}
