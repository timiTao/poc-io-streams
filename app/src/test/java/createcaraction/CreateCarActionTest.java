package createcaraction;

import static org.assertj.core.api.Assertions.assertThat;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

@MicronautTest
class CreateCarActionTest {

  @Inject
  @Client("/v1/cars")
  HttpClient httpClient;

  @Test
  public void when_send_correct_request_then_accept_it() {
    HttpRequest<String> request = HttpRequest.POST("", null);
    var response = httpClient.toBlocking().exchange(request);
    assertThat(response).isNotNull();
    assertThat(response.getStatus().getCode()).isEqualTo(202);
  }
}
