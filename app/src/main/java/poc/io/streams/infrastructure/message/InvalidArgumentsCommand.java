package poc.io.streams.infrastructure.message;

import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaTitle;
import io.micronaut.core.annotation.Introspected;

@Introspected
@JsonSchemaTitle(InvalidArgumentsCommand.EVENT_TYPE + "-value")
public class InvalidArgumentsCommand extends AbstractMessage {

  public static final String EVENT_TYPE = "invalid-argument-command";

  public InvalidArgumentsCommand(String id) {
    super(id, InvalidArgumentsCommand.EVENT_TYPE);
  }
}
