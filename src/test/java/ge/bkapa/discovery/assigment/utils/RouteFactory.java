package ge.bkapa.discovery.assigment.utils;

import ge.bkapa.discovery.assigment.model.entity.Planet;
import ge.bkapa.discovery.assigment.model.entity.Route;

import java.math.BigDecimal;

public class RouteFactory {

    public static Route getRoute(String from, String to, BigDecimal distance) {
        Planet start = new Planet();
        start.setId(from);
        start.setName(from);

        Planet end = new Planet();
        end.setId(to);
        end.setName(to);

        return new Route() {{
            setFrom(start);
            setTo(end);
            setDistance(distance);
        }};
    }

}
