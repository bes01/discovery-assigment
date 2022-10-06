package ge.bkapa.discovery.assigment.model.repository;

import ge.bkapa.discovery.assigment.model.entity.Planet;
import ge.bkapa.discovery.assigment.model.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RouteRepository extends JpaRepository<Route, Long> {
    @Modifying
    @Query("delete from Route r where r.from.id = :planetId or r.to.id = :planetId")
    void deleteByPlanetId(String planetId);

    @Query("select case when count(r)> 0 then true else false end from Route r where (r.from = :a and r.to = :b) or (r.from = :b and r.to = :a)")
    boolean routeBetweenPlanetsExists(Planet a, Planet b);
}
