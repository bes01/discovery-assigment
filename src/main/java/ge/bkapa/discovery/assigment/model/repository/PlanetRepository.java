package ge.bkapa.discovery.assigment.model.repository;

import ge.bkapa.discovery.assigment.model.entity.Planet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanetRepository extends JpaRepository<Planet, String> {
}
