package poc.io.streams.ui.getrequestaction.action;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpResponseFactory;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import jakarta.inject.Inject;
import reactor.core.publisher.Mono;

@Controller
public class GetRequestAction {

  @Inject
  private RequestRepository requestRepository;

  @Get("/v1/request/{id}")
  public Mono<HttpResponse<String>> handle(@PathVariable String id) {

    return requestRepository.find(id)
      .flatMap(request ->  HttpResponseFactory.INSTANCE.status(request.statusCode()))
      .switchIfEmpty(___ -> HttpResponse.notFound());


    return Mono.fromCallable(() -> HttpResponse.ok("Hello " + id));
  }
}
