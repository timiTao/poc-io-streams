package poc.io.streams.topology.createcar;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.messaging.annotation.MessageHeader;
import reactor.core.publisher.Mono;

@KafkaClient(
  acks = KafkaClient.Acknowledge.ALL
)
public abstract class CreateCarProducer {

  public static final String TOPIC_NAME = "car";

  public Mono<Void> handle(Car.CarCreated event) {
    return provide(event.id(), event);
  }

  @Topic(TOPIC_NAME)
  protected abstract Mono<Void> provide(
    @KafkaKey String aggregateOd,
    Car.CarCreated event
  );
}
