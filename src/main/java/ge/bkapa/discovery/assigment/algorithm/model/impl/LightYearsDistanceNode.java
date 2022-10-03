package ge.bkapa.discovery.assigment.algorithm.model.impl;

import ge.bkapa.discovery.assigment.algorithm.model.Node;

import java.math.BigDecimal;

public class LightYearsDistanceNode implements Node {

    private final String from;

    private final String to;

    private final BigDecimal distance;

    public LightYearsDistanceNode(String from, String to, BigDecimal distance) {
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
