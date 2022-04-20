package poc.io.streams.infrastructure.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaTitle;
import io.micronaut.core.annotation.Introspected;

@Introspected
@JsonSchemaTitle(CommandSucceeded.EVENT_TYPE + "-value")
public class CommandSucceeded extends AbstractMessage {

  public static final String EVENT_TYPE = "command-failed";

  public CommandSucceeded(
    @JsonProperty("id") String id
  ) {
    super(id, CommandSucceeded.EVENT_TYPE);
  }
}
