package ge.bkapa.discovery.assigment.boundary;

import ge.bkapa.discovery.assigment.model.dto.PlanetDTO;
import ge.bkapa.discovery.assigment.service.PlanetService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("planets")
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
    public String persistPlanet(@Valid @RequestBody PlanetDTO planetDTO) {
        return service.persistPlanet(planetDTO);
    }

    @PutMapping("{id}")
    public void updatePlanet(@PathVariable String id, @Valid @RequestBody PlanetDTO planetDTO) {
        service.updatePlanet(id, planetDTO);
    }

    @DeleteMapping("{id}")
    public void deletePlanet(@PathVariable String id) {
        service.deletePlanet(id);
    }
}
