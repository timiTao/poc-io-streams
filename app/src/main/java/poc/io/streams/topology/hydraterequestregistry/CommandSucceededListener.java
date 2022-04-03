package poc.io.streams.topology.hydraterequestregistry;

import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.OffsetReset;
import io.micronaut.configuration.kafka.annotation.OffsetStrategy;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.context.annotation.Property;
import io.micronaut.core.util.StringUtils;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import poc.io.streams.infrastructure.message.CommandSucceeded;
import poc.io.streams.infrastructure.message.MessageProducer;

@KafkaListener(
  groupId = "commands-result-listener",
  clientId = "commands-result-listener",
  offsetReset = OffsetReset.EARLIEST,
  offsetStrategy = OffsetStrategy.DISABLED,
  properties = {
    @Property(name = ConsumerConfig.ALLOW_AUTO_CREATE_TOPICS_CONFIG, value = StringUtils.TRUE),
    @Property(name = ConsumerConfig.MAX_POLL_RECORDS_CONFIG, value = "1")
  }
)
public class CommandSucceededListener {

  @Topic(MessageProducer.TOPIC_NAME)
  public void handle(
    CommandSucceeded message,
    Consumer<?, ?> kafkaConsumer) {

  }
}
