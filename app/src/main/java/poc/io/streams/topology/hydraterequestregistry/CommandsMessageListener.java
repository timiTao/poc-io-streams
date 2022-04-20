package poc.io.streams.topology.hydraterequestregistry;

import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.OffsetReset;
import io.micronaut.configuration.kafka.annotation.OffsetStrategy;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.context.annotation.Property;
import io.micronaut.core.util.StringUtils;
import jakarta.inject.Inject;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import poc.io.streams.infrastructure.message.CommandFailed;
import poc.io.streams.infrastructure.message.CommandSucceeded;
import poc.io.streams.infrastructure.message.InvalidArgumentsCommand;
import poc.io.streams.infrastructure.message.MessageProducer;
import poc.io.streams.topology.hydraterequestregistry.RequestRepository.Request;
import reactor.core.publisher.Mono;

@KafkaListener(
  groupId = "commands-result-listener",
  clientId = "commands-result-listener",
  offsetReset = OffsetReset.EARLIEST,
  offsetStrategy = OffsetStrategy.DISABLED,
  properties = {
    @Property(name = ConsumerConfig.ALLOW_AUTO_CREATE_TOPICS_CONFIG, value = StringUtils.TRUE),
    @Property(name = ConsumerConfig.MAX_POLL_RECORDS_CONFIG, value = "1"),
    @Property(
      name = "value.subject.name.strategy",
      value = "io.confluent.kafka.serializers.subject.TopicRecordNameStrategy"
    )
  }
)
public class CommandsMessageListener {
  private final Logger log = LoggerFactory.getLogger(CommandsMessageListener.class);

  @Inject
  private RequestRepository requestRepository;

  @Topic(MessageProducer.TOPIC_NAME)
  public void handle(
    CommandSucceeded message,
    Consumer<?, ?> kafkaConsumer) {

    Mono.fromCallable(() -> new Request(message.id(), Request.State.SUCCESSES))
      .doOnNext(___ -> log.info("TUTAJ CommandSucceeded "))
      .flatMap(request -> requestRepository.save(request))
      .block();
    kafkaConsumer.commitAsync();
  }

  @Topic(MessageProducer.TOPIC_NAME)
  public void handle(
    CommandFailed message,
    Consumer<?, ?> kafkaConsumer) {

    Mono.fromCallable(() -> new Request(message.id(), Request.State.FAILED))
      .doOnNext(___ -> log.info("TUTAJ CommandFailed "))
      .flatMap(request -> requestRepository.save(request))
      .doOnNext(___ -> log.info("TUTAJ CommandFailed redis "))
      .block();
    kafkaConsumer.commitAsync();
  }

  @Topic(MessageProducer.TOPIC_NAME)
  public void handle(
    InvalidArgumentsCommand message,
    Consumer<?, ?> kafkaConsumer) {

    Mono.fromCallable(() -> new Request(message.id(), Request.State.INVALID_ARGUMENTS))
      .doOnNext(___ -> log.info("TUTAJ InvalidArgumentsCommand "))
      .flatMap(request -> requestRepository.save(request))
      .block();
    kafkaConsumer.commitAsync();
  }
}
