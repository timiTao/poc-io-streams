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
    return Mono.deferContextual(Mono::just)
//      .flatMap(ctx -> provide(message.id, message, String.valueOf(ctx.get("correlation-id"))));
      .flatMap(ctx -> provide(message.id, message, "12"));
  }

  public record CreateCarMessage(
    @JsonProperty("id") String id,
    @JsonProperty("name") String name,
    @JsonProperty("vim") String vim,
    @JsonProperty("production_year") String productionYear
  ) {
  }

  public static final String HEADER_CORRELATION_ID = "correlation-id";

  @Topic(TOPIC_NAME)
  protected abstract Mono<Void> provide(
    @KafkaKey String resourceId,
    CreateCarMessage message,
    @MessageHeader(HEADER_CORRELATION_ID) String correlationId
  );
}
