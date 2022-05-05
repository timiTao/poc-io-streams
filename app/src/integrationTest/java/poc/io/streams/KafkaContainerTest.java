package poc.io.streams;
;
import io.confluent.kafka.schemaregistry.client.rest.RestService;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.test.support.TestPropertyProvider;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Testcontainers
public interface KafkaContainerTest extends TestPropertyProvider {
  @Container
  KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:6.2.0"))
    .withNetwork(Network.newNetwork());

  @Container
  SchemaRegistryContainer schemaRegistry = new SchemaRegistryContainer()
    .withKafka(kafka)
    .waitingFor(Wait.forHttp("/subjects").forStatusCode(200))
    .dependsOn(kafka);

  @Override
  @NonNull
  default Map<String, String> getProperties() {
    return Map.of(
      "kafka.bootstrap.servers", kafka.getBootstrapServers()
    );
  }
}
