package ge.bkapa.discovery.assigment.service;

import ge.bkapa.discovery.assigment.model.dto.PlanetDTO;
import ge.bkapa.discovery.assigment.model.dto.RouteDTO;
import ge.bkapa.discovery.assigment.model.entity.Planet;
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
        route.setFrom(planetService.lookup(routeDTO.from().id()));
        route.setTo(planetService.lookup(routeDTO.to().id()));
        route.setDistance(routeDTO.distance());

        if (routeExists(route.getFrom(), route.getTo())) {
            throw new RuntimeException("duplicate_roads");
        }

        return repository.save(route).getId();
    }

    public void updateRoute(Long id, RouteDTO routeDTO) {
        Optional<Route> route = repository.findById(id);
        route.ifPresent(r -> {
            Planet from = planetService.lookup(routeDTO.from().id());
            Planet to = planetService.lookup(routeDTO.to().id());

            if (!sameRoute(r.getFrom(), r.getTo(), routeDTO.from(), routeDTO.to()) && routeExists(from, to)) {
                throw new RuntimeException("duplicate_routes");
            }

            r.setFrom(from);
            r.setTo(to);
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

    private boolean routeExists(Planet a, Planet b) {
        return repository.routeBetweenPlanetsExists(a, b);
    }

    private boolean sameRoute(Planet from, Planet to, PlanetDTO fromDTO, PlanetDTO toDTO) {
        String fromId = from.getId();
        String fromDTOId = fromDTO.id();
        String toId = to.getId();
        String toDTOId = toDTO.id();

        return (fromId.equals(fromDTOId) && toId.equals(toDTOId)) || (fromId.equals(toDTOId) && toId.equals(fromDTOId));
    }
}
