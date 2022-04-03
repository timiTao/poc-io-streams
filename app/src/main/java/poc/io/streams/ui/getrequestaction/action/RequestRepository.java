package poc.io.streams.ui.getrequestaction.action;

import reactor.core.publisher.Mono;

public interface RequestRepository {
  record Request(
    String correlationId,
    String status
  ) {
  }

  Mono<Request> find(String id);
}
