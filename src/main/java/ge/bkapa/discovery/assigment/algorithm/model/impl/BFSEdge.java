package ge.bkapa.discovery.assigment.algorithm.model.impl;

import ge.bkapa.discovery.assigment.algorithm.model.Edge;

import java.math.BigDecimal;

public class BFSEdge implements Edge {

    private final String from;

    private final String to;

    private final BigDecimal distance;

    public BFSEdge(String from, String to, BigDecimal distance) {
        assert !from.equals(to);
        assert distance.compareTo(BigDecimal.ZERO) > 0;

        this.from = from;
        this.to = to;
        this.distance = distance;
    }

    @Override
    public String getFrom() {
        return from;
    }

    @Override
    public String getTo() {
        return to;
    }

    @Override
    public BigDecimal getDistance() {
        return distance;
    }
}
