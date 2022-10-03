package ge.bkapa.discovery.assigment.algorithm.model;

import java.math.BigDecimal;

public interface Node {

    public String getFrom();

    public String getTo();

    default BigDecimal getDistance() {
        return BigDecimal.ONE;
    }
}
