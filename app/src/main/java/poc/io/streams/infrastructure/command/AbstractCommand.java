package poc.io.streams.infrastructure.command;

public abstract class AbstractCommand implements Command {
  private final String id;

  protected AbstractCommand(String id) {
    this.id = id;
  }

  @Override
  public String getId() {
    return id;
  }
}
