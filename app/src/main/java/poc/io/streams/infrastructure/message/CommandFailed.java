package poc.io.streams.infrastructure.message;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CommandFailed extends AbstractMessage {
  public CommandFailed(
    @JsonProperty("id") String id
  ) {
    super(id);
  }
}
