package ge.bkapa.discovery.assigment.boundary;

import ge.bkapa.discovery.assigment.model.dto.RouteDTO;
import ge.bkapa.discovery.assigment.service.RouteService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("route")
public class RouteResource {

    private final RouteService service;

    public RouteResource(RouteService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    public RouteDTO getRouteById(@PathVariable Long id) {
        return service.getRouteById(id);
    }

    @GetMapping
    public List<RouteDTO> getRoutes() {
        return service.getRoutes();
    }

    @PostMapping
    public Long persistRoute(@Valid @RequestBody RouteDTO routeDTO) {
        return service.persistRoute(routeDTO);
    }

    @PutMapping("{id}")
    public void updateRoute(@PathVariable Long id, @Valid @RequestBody RouteDTO routeDTO) {
        service.updateRoute(id, routeDTO);
    }

    @DeleteMapping("{id}")
    public void deleteRoute(@PathVariable Long id) {
        service.deleteRoute(id);
    }
}
