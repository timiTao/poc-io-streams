package poc.io.streams.infrastructure.message;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.messaging.annotation.MessageHeader;
import reactor.core.publisher.Mono;

@KafkaClient(
  acks = KafkaClient.Acknowledge.ALL
)
public abstract class MessageProducer {

  public static final String TOPIC_NAME = "commands-result";

  public Mono<Void> handle(Message message) {
    return provide(
      message.getId(),
      message
    );
  }

  @Topic(TOPIC_NAME)
  protected abstract Mono<Void> provide(
    @KafkaKey String id,
    Message event
  );
}
