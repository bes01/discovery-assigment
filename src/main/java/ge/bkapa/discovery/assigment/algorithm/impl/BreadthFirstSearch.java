package ge.bkapa.discovery.assigment.algorithm.impl;

import ge.bkapa.discovery.assigment.algorithm.ShortestPathAlgorithm;
import ge.bkapa.discovery.assigment.algorithm.model.Edge;
import ge.bkapa.discovery.assigment.algorithm.model.Path;
import ge.bkapa.discovery.assigment.algorithm.model.impl.BFSPath;

import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class BreadthFirstSearch implements ShortestPathAlgorithm {

    @Override
    public Path computeShortestPath(String from, String to, Map<String, List<Edge>> adjacencyMap) {
        if (!adjacencyMap.containsKey(from) || !adjacencyMap.containsKey(to)) return new BFSPath();

        PriorityQueue<Path> paths = initializePriorityQueue(from, adjacencyMap);

        while (!paths.isEmpty()) {
            Path currentPath = paths.poll();
            Edge currentEdge = getLastEdge(currentPath);

            if (currentEdge.getTo().equals(to)) {
                return currentPath;
            }

            for (Edge nextEdge : adjacencyMap.get(currentEdge.getTo())) {
                if (!alreadyBeenHere(currentPath, nextEdge)) {
                    Path nextPath = new BFSPath(currentPath);
                    nextPath.appendEdge(nextEdge);
                    paths.add(nextPath);
                }
            }
        }

        return new BFSPath();
    }

    private boolean alreadyBeenHere(Path currentPath, Edge nextEdge) {
        return currentPath.getPath().stream().anyMatch(e -> e.getFrom().equals(nextEdge.getTo()) || e.getTo().equals(nextEdge.getTo()));
    }

    private Edge getLastEdge(Path currentPath) {
        return currentPath.getPath().get(currentPath.getPath().size() - 1);
    }

    private PriorityQueue<Path> initializePriorityQueue(String from, Map<String, List<Edge>> adjacencyMap) {
        PriorityQueue<Path> paths = new PriorityQueue<>();
        for (Edge startEdge : adjacencyMap.get(from)) {
            Path startPath = new BFSPath();
            startPath.appendEdge(startEdge);
            paths.add(startPath);
        }
        return paths;
    }
}
