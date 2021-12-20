import com.andy.httpserver.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoutesShould {

    private Routes routes;

    @BeforeEach
    void setup() {
        routes = new Routes();
    }

    @Test
    void find_route() {
        routes.addRoute("/route1", new Route(HttpMethod.GET, "somefink here"));
        HttpResponse httpResponse = routes.process(new HttpRequest(HttpMethod.GET, "localhost", "/route1", ""));
        assertEquals(HttpStatus.OK, httpResponse.getStatus());
    }

    @Test
    void not_find_route() {
        routes.addRoute("/route1", new Route(HttpMethod.GET, "somefink here"));
        HttpResponse httpResponse = routes.process(new HttpRequest(HttpMethod.POST, "localhost", "/doesntexist", ""));
        assertEquals(HttpStatus.NOT_FOUND, httpResponse.getStatus());
    }

    @Test
    void give_method_not_allowed() {
        routes.addRoute("/route1", new Route(HttpMethod.GET, "somefink here"));
        HttpResponse httpResponse = routes.process(new HttpRequest(HttpMethod.POST, "localhost", "/route1", ""));
        assertEquals(HttpStatus.NOT_ALLOWED, httpResponse.getStatus());
    }


}
