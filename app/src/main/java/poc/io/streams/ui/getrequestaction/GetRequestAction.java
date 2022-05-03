package poc.io.streams.ui.getrequestaction;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

@Controller
public class GetRequestAction {
  private final Logger log = LoggerFactory.getLogger(GetRequestAction.class);

  @Inject
  private RequestRepository requestRepository;

  @Inject
  private ObjectMapper objectMapper;

  @Get("/v1/request/{id}")
  public Mono<MutableHttpResponse<String>> handle(@PathVariable String id) {

    return requestRepository.find(id)
      .doOnNext(___ -> log.info("requestRepository.find " + id))
      .flatMap(this::displayRequest)
      .doOnNext(response -> log.info("response.find " + response.status()))
      .defaultIfEmpty(HttpResponse.notFound());
  }

  private Mono<MutableHttpResponse<String>> displayRequest(RequestRepository.Request request) {
    try {
      return Mono.just(HttpResponse.ok(objectMapper.writeValueAsString(request)));
    } catch (JsonProcessingException e) {
      return Mono.just(HttpResponse.serverError(e.getMessage()));
    }
  }
}
