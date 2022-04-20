package poc.io.streams.infrastructure.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaTitle;
import io.micronaut.core.annotation.Introspected;

@Introspected
@JsonSchemaTitle(CommandFailed.EVENT_TYPE + "-value")
public class CommandFailed extends AbstractMessage {
  public static final String EVENT_TYPE = "command-failed";

  public CommandFailed(
    @JsonProperty("id") String id
  ) {
    super(id, CommandFailed.EVENT_TYPE);
  }
}
