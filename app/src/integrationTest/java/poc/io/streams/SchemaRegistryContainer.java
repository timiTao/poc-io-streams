package poc.io.streams;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.utility.DockerImageName;

public class SchemaRegistryContainer extends GenericContainer<SchemaRegistryContainer> {
  private static final int SCHEMA_REGISTRY_EXPOSED_PORT = 8081;

  public SchemaRegistryContainer() {
    super(DockerImageName.parse("confluentinc/cp-schema-registry:6.2.0"));
    withExposedPorts(SCHEMA_REGISTRY_EXPOSED_PORT);
  }

  public SchemaRegistryContainer withKafka(KafkaContainer kafka) {
    return withKafka(kafka.getNetwork(), kafka.getNetworkAliases().get(0) + ":9092");
  }

  public SchemaRegistryContainer withKafka(Network network, String bootstrapServers) {
    withNetwork(network);
    withEnv("SCHEMA_REGISTRY_HOST_NAME", "schema-registry");
    withEnv("SCHEMA_REGISTRY_LISTENERS", "http://0.0.0.0:" + SCHEMA_REGISTRY_EXPOSED_PORT);
    withEnv("SCHEMA_REGISTRY_KAFKASTORE_BOOTSTRAP_SERVERS", "PLAINTEXT://" + bootstrapServers);
    return self();
  }

  public String getSchemaRegistryUrl() {
    return "http://" + getContainerIpAddress() + ":" + getMappedPort(SCHEMA_REGISTRY_EXPOSED_PORT);
  }
}
