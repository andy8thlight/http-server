package com.andy.httpserver;

import io.restassured.RestAssured;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.net.URL;

import static com.andy.httpserver.HttpMethod.*;

public class HttpServerSetupExtension implements BeforeAllCallback, ExtensionContext.Store.CloseableResource {
    private static boolean started = false;
    static final int PORT_NUMBER = 5555;
    private ExampleHttpServer httpServer;

    @Override
    public void beforeAll(ExtensionContext context) {
        if (!started) {
            RestAssured.baseURI = "http://localhost:" + PORT_NUMBER;
            httpServer = new ExampleHttpServer(PORT_NUMBER, createTestRoutes());
            httpServer.startHttpServer();
            started = true;
        }
    }

    static private Routes createTestRoutes() {
        Routes routes = new Routes(getContentRoot());
        routes.addRoute("/simple_get_with_body", new Route(GET, new GetAction(new FileContent("simple_get_with_body", ContentType.TEXT_PLAIN, getContentRoot()))));
        routes.addRoute("/simple_get", new Route(GET, new GetAction(new FileContent("simple_get", ContentType.TEXT_PLAIN, getContentRoot()))));
        routes.addRoute("/echo_body", new Route(POST, new PostAction(new BasicContent(ContentType.TEXT_PLAIN))));
        routes.addRoute("/head_request", new Route(HEAD, new GetAction(new BasicContent("", ContentType.TEXT_PLAIN))));
        routes.addRoute("/method_options", new Route(GET, new GetAction(new FileContent("method_options", ContentType.TEXT_PLAIN, getContentRoot()))));
        routes.addRoute("/method_options2", new Route(GET, new GetAction(new FileContent("method_options2", ContentType.TEXT_PLAIN, getContentRoot()))));
        routes.addRoute("/method_options2", new Route(PUT, new GetAction(new FileContent("method_options2", ContentType.TEXT_PLAIN, getContentRoot()))));
        routes.addRoute("/method_options2", new Route(POST, new GetAction(new FileContent("method_options2", ContentType.TEXT_PLAIN, getContentRoot()))));
        routes.addRoute("/redirect", new Route(GET, new RedirectAction("http://0.0.0.0:5000/simple_get")));
        routes.addRoute("/text_response", new Route(GET, new GetAction(new FileContent("text_response", ContentType.TEXT_PLAIN, getContentRoot()))));
        routes.addRoute("/html_response", new Route(GET, new GetAction(new BasicContent("<html><body><p>HTML Response</p></body></html>", ContentType.TEXT_HTML))));
        routes.addRoute("/json_response", new Route(GET, new GetAction(new BasicContent("{ key1: 'value1', key2: 'value2' }", ContentType.JSON))));
        routes.addRoute("/xml_response", new Route(GET, new GetAction(new BasicContent("<note><body>XML Response</body></note>", ContentType.XML))));
        routes.addRoute("/health-check.html", new Route(GET, new GetAction(new FileContent("health-check.html", ContentType.TEXT_HTML, getContentRoot()))));

        return routes;
    }

    private static String getContentRoot() {
        URL resource = HttpServerSetupExtension.class.getResource("/content/");
        return resource.getPath();
    }

    @Override
    public void close() {
        started = false;
        httpServer.shutdown();
    }
}
