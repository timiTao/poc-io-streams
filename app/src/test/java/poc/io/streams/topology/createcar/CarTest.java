package poc.io.streams.topology.createcar;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class CarTest {

  @Test
  void testCreateCar() {
    var car = new Car("1", "test", "1234", "1999");

    assertThat(car.getEvents()).hasSize(1);
    assertThat(car.getEvents().get(0)).isInstanceOf(Car.CarCreated.class);
  }

}
