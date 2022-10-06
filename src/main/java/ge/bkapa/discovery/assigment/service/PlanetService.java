package ge.bkapa.discovery.assigment.service;

import ge.bkapa.discovery.assigment.model.dto.PlanetDTO;
import ge.bkapa.discovery.assigment.model.entity.Planet;
import ge.bkapa.discovery.assigment.model.repository.PlanetRepository;
import ge.bkapa.discovery.assigment.model.repository.RouteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PlanetService {

    private final PlanetRepository repository;

    private final RouteRepository routeRepository;

    public PlanetService(PlanetRepository repository, RouteRepository routeRepository) {
        this.repository = repository;
        this.routeRepository = routeRepository;
    }

    public List<PlanetDTO> getPlanets() {
        return repository.findAll().stream().map(this::mapEntityToDTO).collect(Collectors.toList());
    }

    public Planet lookup(String id) {
        return repository.findById(id).orElseThrow();
    }

    public PlanetDTO getPlanetById(String id) {
        return repository.findById(id).map(this::mapEntityToDTO).orElseThrow();
    }

    public String persistPlanet(PlanetDTO planetDTO) {
        Planet planet = new Planet();
        planet.setId(planetDTO.id());
        planet.setName(planetDTO.name());
        return repository.save(planet).getId();
    }

    public void updatePlanet(String id, PlanetDTO planetDTO) {
        Optional<Planet> planet = repository.findById(id);
        planet.ifPresent(p -> {
            p.setName(planetDTO.name());
            repository.save(p);
        });
    }

    public void deletePlanet(String id) {
        routeRepository.deleteByPlanetId(id);
        repository.deleteById(id);
    }

    private PlanetDTO mapEntityToDTO(Planet entity) {
        return new PlanetDTO(entity.getId(), entity.getName());
    }
}
