package poc.io.streams.ui.createcaraction;

import static poc.io.streams.ui.createcaraction.CreateCarProducer.HEADER_CORRELATION_ID;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import java.util.UUID;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import reactor.core.publisher.Mono;

@Controller
public class CreateCarAction {

  private final CreateCarProducer producer;

  public CreateCarAction(CreateCarProducer producer) {
    this.producer = producer;
  }

  @Post("/v1/cars")
  public Mono<HttpResponse<String>> handle(@Body @NotEmpty CreateCarRequest request) {
    String correlationId = UUID.randomUUID().toString();

    return producer.handle(request.toMessage())
      .contextWrite(ctx -> ctx.put(HEADER_CORRELATION_ID, correlationId))
      .thenReturn(HttpResponse.accepted(HttpResponse.uri("v1/request/" + correlationId)));
  }

  public record CreateCarRequest(
    @NotBlank String id,
    @NotBlank String name,
    @NotBlank String vim,
    @NotBlank @Size(min = 4, max = 4) String productionYear
  ) {
    CreateCarProducer.CreateCarMessage toMessage() {
      return new CreateCarProducer.CreateCarMessage(
        this.id,
        this.name,
        this.vim,
        this.productionYear
      );
    }
  }
}
