package poc.io.streams.topology.createcar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.validation.constraints.NotBlank;

public class Car {
  public record CarCreated(
    @NotBlank String id,
    @NotBlank String name,
    @NotBlank String vim,
    @NotBlank String productionYear
  ) {
  }

  public record NameChanged(
    @NotBlank String id,
    @NotBlank String name
  ) {
  }

  public interface Event {
    String id();
  }


  private String id, name, vim, productionYear;

  public Car(String id, String name, String vim, String productionYear) {
    this.id = id;
    this.name = name;
    this.vim = vim;
    this.productionYear = productionYear;
  }

  public Car(List<CarCreated> list) {

  }

  public List<CarCreated> getEvents() {
    return new ArrayList<>(Collections.singleton(new CarCreated(id, name, vim, productionYear)));
  }

  private void apply(CarCreated carCreated) {
    this.id = carCreated.id();
    this.name = carCreated.name;
    this.vim = carCreated.vim;
    this.productionYear = carCreated.productionYear;
  }
}
