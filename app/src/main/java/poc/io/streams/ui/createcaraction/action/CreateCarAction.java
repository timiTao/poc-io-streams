package poc.io.streams.ui.createcaraction.action;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;
import java.util.UUID;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import poc.io.streams.ui.createcaraction.producer.CreateCarProducer;
import reactor.core.publisher.Mono;

@Controller
public class CreateCarAction {

  @Inject
  private CreateCarProducer producer;

  @Post("/v1/cars")
  public Mono<HttpResponse<String>> handle(@Body @NotEmpty CreateCarRequest request) {
    String requestId = UUID.randomUUID().toString();
    return producer.handle(request.toMessage(requestId))
      .thenReturn(HttpResponse.accepted(HttpResponse.uri("v1/request/" + requestId)));
  }

  public record CreateCarRequest(
    @NotBlank String id,
    @NotBlank String name,
    @NotBlank String vim,
    @NotBlank @Size(min = 4, max = 4) String productionYear
  ) {
    CreateCarProducer.CreateCarMessage toMessage(String requestId) {
      return new CreateCarProducer.CreateCarMessage(
        requestId,
        this.id,
        this.name,
        this.vim,
        this.productionYear
      );
    }
  }
}
