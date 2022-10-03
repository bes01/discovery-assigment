package ge.bkapa.discovery.assigment.service;

import ge.bkapa.discovery.assigment.model.dto.PlanetDTO;
import ge.bkapa.discovery.assigment.model.dto.RouteDTO;
import ge.bkapa.discovery.assigment.model.entity.Route;
import ge.bkapa.discovery.assigment.model.repository.RouteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class RouteService {

    private final RouteRepository repository;

    private final PlanetService planetService;

    public RouteService(RouteRepository repository, PlanetService planetService) {
        this.repository = repository;
        this.planetService = planetService;
    }

    public RouteDTO getRouteById(Long id) {
        return repository.findById(id).map(this::mapEntityToDTO).orElseThrow();
    }

    public List<RouteDTO> getRoutes() {
        return repository.findAll().stream().map(this::mapEntityToDTO).collect(Collectors.toList());
    }

    public Long persistRoute(RouteDTO routeDTO) {
        Route route = new Route();
        route.setFrom(planetService.lookup(route.getFrom().getId()));
        route.setTo(planetService.lookup(route.getTo().getId()));
        route.setDistance(routeDTO.distance());
        return repository.save(route).getId();
    }

    public void updateRoute(Long id, RouteDTO routeDTO) {
        Optional<Route> route = repository.findById(id);
        route.ifPresent(r -> {
            r.setFrom(planetService.lookup(r.getFrom().getId()));
            r.setTo(planetService.lookup(r.getTo().getId()));
            r.setDistance(routeDTO.distance());
            repository.save(r);
        });
    }

    public void deleteRoute(Long id) {
        repository.deleteById(id);
    }

    private RouteDTO mapEntityToDTO(Route entity) {
        PlanetDTO from = new PlanetDTO(entity.getFrom().getId(), entity.getFrom().getName());
        PlanetDTO to = new PlanetDTO(entity.getTo().getId(), entity.getTo().getName());

        return new RouteDTO(entity.getId(), from, to, entity.getDistance());
    }
}
