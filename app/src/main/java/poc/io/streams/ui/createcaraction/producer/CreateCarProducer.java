package poc.io.streams.ui.createcaraction.producer;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.messaging.annotation.MessageHeader;
import reactor.core.publisher.Mono;

@KafkaClient(
  acks = KafkaClient.Acknowledge.ALL
)
public abstract class CreateCarProducer {

  public Mono<Void> handle(CreateCarMessage message) {
    return provide(message.id, message, message.requestId);
  }

  @Topic("input-create-car")
  abstract protected Mono<Void> provide(
    @KafkaKey String resourceId,
    CreateCarMessage message,
    @MessageHeader("request-id") String requestId
  );

  public record CreateCarMessage(
    String requestId,
    String id,
    String name,
    String vim,
    String productionYear
  ) {
  }
}
