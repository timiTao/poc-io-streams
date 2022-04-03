package poc.io.streams.topology.createcar;

import javax.validation.constraints.NotBlank;

public class Car {

  public record CarCreated(
    @NotBlank String id,
    @NotBlank String name,
    @NotBlank String vim,
    @NotBlank String productionYear
  ) {
  }
}
