package poc.io.streams.infrastructure.message;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CommandSucceeded extends AbstractMessage {
  public CommandSucceeded(
    @JsonProperty("id") String id
  ) {
    super(id);
  }
}
