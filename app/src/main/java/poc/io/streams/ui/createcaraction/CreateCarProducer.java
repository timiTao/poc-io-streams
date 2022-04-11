package poc.io.streams.ui.createcaraction;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.messaging.annotation.MessageHeader;
import reactor.core.publisher.Mono;

@KafkaClient(
  acks = KafkaClient.Acknowledge.ALL
)
public abstract class CreateCarProducer {

  public static final String TOPIC_NAME = "input-create-car";

  public Mono<Void> handle(CreateCarMessage message) {
    return provide(message.id, message, message.correlationId);
  }

  public record CreateCarMessage(
    @JsonProperty("correlation_id") String correlationId,
    @JsonProperty("id") String id,
    @JsonProperty("name") String name,
    @JsonProperty("vim") String vim,
    @JsonProperty("production_year") String productionYear
  ) {
  }

  @Topic(TOPIC_NAME)
  protected abstract Mono<Void> provide(
    @KafkaKey String resourceId,
    CreateCarMessage message,
    @MessageHeader("correlation-id") String correlationId
  );
}
