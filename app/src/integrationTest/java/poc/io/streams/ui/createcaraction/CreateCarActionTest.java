package poc.io.streams.ui.createcaraction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import com.fasterxml.jackson.databind.JsonNode;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.OffsetReset;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.context.annotation.Property;
import io.micronaut.core.util.StringUtils;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.messaging.annotation.MessageBody;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import poc.io.streams.BaseContainersTest;

@MicronautTest
class CreateCarActionTest implements BaseContainersTest {

  @Inject
  @Client("/v1/cars")
  HttpClient httpClient;

  @Inject
  TestMessageConsumer testMessageConsumer;

  @Test
  public void when_send_correct_request_then_accept_it() {
    HttpRequest<String> request = HttpRequest.POST("", getCorrectRequest());
    var response = httpClient.toBlocking().exchange(request, JsonNode.class);
    assertThat(response).isNotNull();
    assertEquals(response.getStatus(), HttpStatus.ACCEPTED);

    Awaitility.await()
      .timeout(Duration.ofSeconds(10))
      .untilAsserted(() -> {
        assertEquals(1, testMessageConsumer.messages.size());
        assertInstanceOf(CreateCarProducer.CreateCarMessage.class, testMessageConsumer.messages.get(0));
      });
  }

  private String getCorrectRequest() {
    return """
      {
          "id": 1,
          "name": "test1",
          "vim": "1234",
          "productionYear": 1982
      }
      """;
  }

  @Test
  public void when_send_bad_request_then_reject_it() {
    HttpRequest<String> request = HttpRequest.POST("", null);
    HttpClientResponseException thrown = catchThrowableOfType(
      () -> httpClient.toBlocking().exchange(request),
      HttpClientResponseException.class
    );

    assertThat(thrown).isNotNull();
    assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatus());
  }

  @KafkaListener(
    offsetReset = OffsetReset.EARLIEST,
    properties = {
      @Property(name = ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, value = StringUtils.TRUE),
    }
  )
  public static class TestMessageConsumer {
    List<CreateCarProducer.CreateCarMessage> messages = new ArrayList<>();

    @Topic(CreateCarProducer.TOPIC_NAME)
    void consume(@MessageBody CreateCarProducer.CreateCarMessage message) {
      messages.add(message);
    }
  }
}
