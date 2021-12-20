import com.andy.httpserver.HttpMethod;
import com.andy.httpserver.Route;
import com.andy.httpserver.Routes;
import com.andy.httpserver.HttpRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoutesShould {

    @Test
    void find_route() {
        Routes routes = new Routes();
        routes.addRoute("/route1", new Route(HttpMethod.GET, "somefink here"));
        Route route = routes.getRoute(new HttpRequest(HttpMethod.GET, "localhost", "/route1", ""));
        assertEquals("somefink here", route.getBody());
    }

    @Test
    void not_find_route() {
        Routes routes = new Routes();
        routes.addRoute("/route1", new Route(HttpMethod.GET, "somefink here"));
        Route route = routes.getRoute(new HttpRequest(HttpMethod.POST, "localhost", "/route1", ""));
        assertEquals("somefink here", route.getBody());
    }


}
