package poc.io.streams.ui.getrequestaction;

import io.micronaut.context.annotation.Bean;
import reactor.core.publisher.Mono;

@Bean
public class RequestRepository {
  record Request(
    String id,
    State state
  ) {
    public enum State {
      PROCESSING, SUCCESSES, FAILED, INVALID_ARGUMENTS
    }
  }

  Mono<Request> find(String id) {
    return Mono.empty();
  }
}
