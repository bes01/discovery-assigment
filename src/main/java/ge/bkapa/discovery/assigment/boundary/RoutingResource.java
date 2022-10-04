package ge.bkapa.discovery.assigment.boundary;

import ge.bkapa.discovery.assigment.algorithm.model.Path;
import ge.bkapa.discovery.assigment.service.RoutingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("routing")
public class RoutingResource {

    private final RoutingService service;

    public RoutingResource(RoutingService service) {
        this.service = service;
    }

    @GetMapping
    public Path getDistanceBetweenPlanets(@RequestParam String from, @RequestParam String to) {
        return service.computeDistanceBetweenPlanets(from, to);
    }

    @GetMapping("from-earth")
    public Path getDistanceFromEarth(@RequestParam String to) {
        return service.computeDistanceFromEarth(to);
    }
}
