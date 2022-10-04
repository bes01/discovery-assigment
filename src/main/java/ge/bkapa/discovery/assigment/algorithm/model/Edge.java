package ge.bkapa.discovery.assigment.algorithm.model;

import java.math.BigDecimal;

public interface Edge {

    String getFrom();

    String getTo();

    default BigDecimal getDistance() {
        return BigDecimal.ONE;
    }
}
