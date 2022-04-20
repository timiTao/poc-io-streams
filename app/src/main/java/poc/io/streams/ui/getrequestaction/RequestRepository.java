package poc.io.streams.ui.getrequestaction;

import io.micronaut.context.annotation.Bean;
import jakarta.inject.Inject;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import reactor.core.publisher.Mono;

@Bean
public class RequestRepository {
  @Inject
  private RedissonClient client;

  record Request(
    String id,
    State state
  ) {
    public enum State {
      PROCESSING, SUCCESSES, FAILED, INVALID_ARGUMENTS
    }
  }

  Mono<Request> find(String id) {
    return Mono.fromCallable(() -> client.getBucket("request:" + id, JsonJacksonCodec.INSTANCE))
      .map(RBucket::get)
      .map(result -> (Request) result);
  }
}
