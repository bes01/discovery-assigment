package ge.bkapa.discovery.assigment.algorithm.model.impl;

import ge.bkapa.discovery.assigment.algorithm.model.Edge;
import ge.bkapa.discovery.assigment.algorithm.model.Path;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BFSPath implements Path {

    private final List<Edge> edges;

    private BigDecimal length;

    public BFSPath() {
        this(new ArrayList<>(), new BigDecimal("0"));
    }

    public BFSPath(List<Edge> edges, BigDecimal length) {
        this.edges = new ArrayList<>(edges);
        this.length = length;
    }

    public BFSPath(Path path) {
        this(path.getPath(), path.getLength());
    }

    @Override
    public BigDecimal getLength() {
        return length;
    }

    @Override
    public List<Edge> getPath() {
        return edges;
    }

    @Override
    public void appendEdge(Edge edge) {
        length = length.add(edge.getDistance());
        edges.add(edge);
    }

    @Override
    public int compareTo(Path o) {
        return length.compareTo(o.getLength());
    }
}
