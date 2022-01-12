package com.andy.httpserver;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(HttpServerSetupExtension.class)
public class StructuredDataTest {

    @Test
    void should_get_a_text_response() {
        given().
            get("/text_response").
        then().
            statusCode(200).
            body(equalTo("text response")).
            header("Content-Type", equalTo("text/plain"));
    }

    @Test
    void should_get_a_html_response() {
        given().
            get("/html_response").
        then().
            statusCode(200).
            body(equalTo("<html><body><p>HTML Response</p></body></html>")).
            header("Content-Type", equalTo("text/html"));
    }

    @Test
    void should_get_a_json_response() {
        given().
            get("/json_response").
        then().
            statusCode(200).
            body(equalTo("{ key1: 'value1', key2: 'value2' }")).
            header("Content-Type", equalTo("application/json"));
    }

    @Test
    void should_get_a_xml_response() {
        given().
            get("/xml_response").
        then().
            statusCode(200).
            body(equalTo("<note><body>XML Response</body></note>")).
            header("Content-Type", equalTo("text/xml"));
    }
}
