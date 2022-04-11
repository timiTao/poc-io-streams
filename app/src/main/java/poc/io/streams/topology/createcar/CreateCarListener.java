package poc.io.streams.topology.createcar;

import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.OffsetReset;
import io.micronaut.configuration.kafka.annotation.OffsetStrategy;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.context.annotation.Property;
import io.micronaut.core.util.StringUtils;
import io.micronaut.messaging.annotation.MessageHeader;
import jakarta.inject.Inject;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import poc.io.streams.infrastructure.message.CommandSucceeded;
import poc.io.streams.infrastructure.message.MessageProducer;
import poc.io.streams.ui.createcaraction.CreateCarProducer;

@KafkaListener(
  groupId = "create-car-listener",
  clientId = "create-car-listener",
  offsetReset = OffsetReset.EARLIEST,
  offsetStrategy = OffsetStrategy.DISABLED,
  properties = {
    @Property(name = ConsumerConfig.ALLOW_AUTO_CREATE_TOPICS_CONFIG, value = StringUtils.TRUE),
    @Property(name = ConsumerConfig.MAX_POLL_RECORDS_CONFIG, value = "1")
  }
)
public class CreateCarListener {
  @Inject
  private MessageProducer messageProducer;

  @Inject
  private CreateCarProducer createCarProducer;

  @Topic(CreateCarProducer.TOPIC_NAME)
  public void handle(
    CreateCarProducer.CreateCarMessage message,
    @MessageHeader("correlation-id") String correlationId,
    Consumer<?, ?> kafkaConsumer) {

    //    createCarProducer.handle()
    messageProducer.handle(new CommandSucceeded(correlationId)).block();
    kafkaConsumer.commitAsync();
  }
}
