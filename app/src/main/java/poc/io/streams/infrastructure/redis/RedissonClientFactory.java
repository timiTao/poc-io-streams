package poc.io.streams.infrastructure.redis;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Value;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

@Factory
public class RedissonClientFactory {

  @Bean
  RedissonClient redissonClient(@Value("${redis.url}") String url) {
    Config config = new Config();
    config.useSingleServer().setAddress(url);

    return Redisson.create(config);
  }
}
