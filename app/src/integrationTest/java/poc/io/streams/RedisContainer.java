package poc.io.streams;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

public class RedisContainer extends GenericContainer<RedisContainer> {

  public RedisContainer() {
    super(DockerImageName.parse("redis:latest"));
    withExposedPorts(6379);
  }
}
