package ge.bkapa.discovery.assigment.service;

import ge.bkapa.discovery.assigment.algorithm.ShortestPathAlgorithm;
import ge.bkapa.discovery.assigment.algorithm.impl.BreadthFirstSearch;
import ge.bkapa.discovery.assigment.algorithm.model.Edge;
import ge.bkapa.discovery.assigment.algorithm.model.Path;
import ge.bkapa.discovery.assigment.algorithm.model.impl.BFSEdge;
import ge.bkapa.discovery.assigment.model.dto.RouteDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class RoutingService {

    private final RouteService routeService;

    private static final String EARTH_ID_CODE = "A";

    public RoutingService(RouteService routeService) {
        this.routeService = routeService;
    }

    public Path computeDistanceFromEarth(String to) {
        return computeDistanceBetweenPlanets(EARTH_ID_CODE, to);
    }

    public Path computeDistanceBetweenPlanets(String from, String to) {
        List<RouteDTO> routes = routeService.getRoutes();
        Map<String, List<Edge>> adjacencyMap = constructAdjacencyMap(routes);

        ShortestPathAlgorithm algorithm = new BreadthFirstSearch();

        return algorithm.computeShortestPath(from, to, adjacencyMap);
    }

    private Map<String, List<Edge>> constructAdjacencyMap(List<RouteDTO> routes) {
        Map<String, List<Edge>> adjacencyMap = new HashMap<>();
        for (RouteDTO routeDTO : routes) {
            String from = routeDTO.from().id();
            String to = routeDTO.to().id();
            BigDecimal distance = routeDTO.distance();

            adjacencyMap.computeIfAbsent(from, k -> new ArrayList<>());
            adjacencyMap.computeIfPresent(from, (k, v) -> {
                v.add(new BFSEdge(from, to, distance));
                return v;
            });

            adjacencyMap.computeIfAbsent(to, k -> new ArrayList<>());
            adjacencyMap.computeIfPresent(to, (k, v) -> {
                v.add(new BFSEdge(to, from, distance));
                return v;
            });
        }
        return adjacencyMap;
    }
}
