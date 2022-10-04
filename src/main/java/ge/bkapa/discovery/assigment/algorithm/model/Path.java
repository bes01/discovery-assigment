package ge.bkapa.discovery.assigment.algorithm.model;

import java.math.BigDecimal;
import java.util.List;

public interface Path extends Comparable<Path>  {

    BigDecimal getLength();

    List<Edge> getPath();

    void appendEdge(Edge edge);
}
