package poc.io.streams.topology.hydraterequestregistry;

import jakarta.inject.Inject;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import reactor.core.publisher.Mono;

public class RequestRepository {

  @Inject
  private RedissonClient client;

  Mono<Void> save(Request request) {
    return Mono.fromCallable(() -> client.getBucket("request:" + request.id, JsonJacksonCodec.INSTANCE))
      .doOnNext(bucket -> bucket.set(request))
      .then();
  }

  public record Request(
    String id,
    State state
  ) {
    public enum State {
      PROCESSING, SUCCESSES, FAILED, INVALID_ARGUMENTS
    }
  }
}
