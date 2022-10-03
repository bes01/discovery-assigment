package ge.bkapa.discovery.assigment.model.dto;


import javax.validation.constraints.NotBlank;

public record PlanetDTO(
        String id,
        @NotBlank String name
) {
}
