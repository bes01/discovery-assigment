package ge.bkapa.discovery.assigment.model.dto;

import com.sun.istack.NotNull;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public record RouteDTO(
        Long id,
        @NotNull @Valid PlanetDTO from,
        @NotNull @Valid PlanetDTO to,
        @NotNull @Size BigDecimal distance
) {
}
