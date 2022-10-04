package ge.bkapa.discovery.assigment.algorithm;

import ge.bkapa.discovery.assigment.algorithm.model.Edge;
import ge.bkapa.discovery.assigment.algorithm.model.Path;

import java.util.List;
import java.util.Map;

public interface ShortestPathAlgorithm {

    Path computeShortestPath(String from, String to, Map<String, List<Edge>> adjacencyMap);

}
