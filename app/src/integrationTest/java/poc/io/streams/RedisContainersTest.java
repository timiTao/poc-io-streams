package poc.io.streams;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.test.support.TestPropertyProvider;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.junit.jupiter.Container;

public interface RedisContainersTest extends TestPropertyProvider {

  @Container
  RedisContainer redisContainer = new RedisContainer();

  @BeforeAll
  static void setupRedisServer() {
    redisContainer.start();
  }

  @Override
  @NonNull
  default Map<String, String> getProperties() {
    return Map.of(
      "redis.url",
      String.format("redis://%s:%s", redisContainer.getContainerIpAddress(), redisContainer.getFirstMappedPort())
    );
  }
}
