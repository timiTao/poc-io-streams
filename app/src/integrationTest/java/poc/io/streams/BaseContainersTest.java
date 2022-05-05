package poc.io.streams;

import io.micronaut.core.annotation.NonNull;
import java.util.HashMap;
import java.util.Map;

public interface BaseContainersTest extends KafkaContainerTest, RedisContainersTest{
  @Override
  @NonNull
  default Map<String, String> getProperties() {
    var map = new HashMap<String, String>();
    map.putAll(KafkaContainerTest.super.getProperties());
    map.putAll(RedisContainersTest.super.getProperties());
    return map;
  }
}
