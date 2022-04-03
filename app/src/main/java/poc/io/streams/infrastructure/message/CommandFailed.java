package poc.io.streams.infrastructure.message;

public class CommandFailed extends AbstractMessage {
  public CommandFailed(String id) {
    super(id);
  }
}
