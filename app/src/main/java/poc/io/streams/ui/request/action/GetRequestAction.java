package poc.io.streams.ui.request.action;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import reactor.core.publisher.Mono;

@Controller
public class GetRequestAction {
  @Get("/v1/request/{id}")
  public Mono<HttpResponse<String>> handle(@PathVariable String id) {
    return Mono.fromCallable(() -> HttpResponse.ok("Hello " + id));
  }
}
