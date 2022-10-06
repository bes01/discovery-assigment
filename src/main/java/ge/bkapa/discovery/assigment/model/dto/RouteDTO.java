package ge.bkapa.discovery.assigment.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

public record RouteDTO(
        @JsonProperty(access = JsonProperty.Access.READ_ONLY) Long id,
        @NotNull @Valid PlanetDTO from,
        @NotNull @Valid PlanetDTO to,
        @NotNull @DecimalMin(value = "0") BigDecimal distance
) {

    @JsonIgnore
    @AssertTrue
    public boolean isValid(){
        return !from.id().equals(to.id());
    }

}
