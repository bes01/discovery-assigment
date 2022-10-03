package ge.bkapa.discovery.assigment.algorithm;

import ge.bkapa.discovery.assigment.algorithm.model.Node;

import java.math.BigDecimal;
import java.util.Map;

public interface ShortestPathAlgorithm {

    BigDecimal computeShortestPath(String from, String to, Map<String, Node> adjacencyMap);

}
