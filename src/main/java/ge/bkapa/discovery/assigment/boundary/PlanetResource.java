package ge.bkapa.discovery.assigment.boundary;

import ge.bkapa.discovery.assigment.model.dto.PlanetDTO;
import ge.bkapa.discovery.assigment.service.PlanetService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController("planets")
public class PlanetResource {

    private final PlanetService service;

    public PlanetResource(PlanetService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    public PlanetDTO getPlanetById(@PathVariable String id) {
        return service.getPlanetById(id);
    }

    @GetMapping
    public List<PlanetDTO> getPlanets() {
        return service.getPlanets();
    }

    @PostMapping
    public String persistPlanet(@Valid PlanetDTO planetDTO) {
        return service.persistPlanet(planetDTO);
    }

    @PutMapping("{id}")
    public void updatePlanet(@PathVariable String id, @Valid PlanetDTO planetDTO) {
        service.updatePlanet(id, planetDTO);
    }

    @DeleteMapping("{id}")
    public void deletePlanet(@PathVariable String id) {
        service.deletePlanet(id);
    }
}
