package poc.io.streams.infrastructure.message;

public abstract class AbstractMessage implements Message {
  private final String id;

  protected AbstractMessage(String id) {
    this.id = id;
  }

  @Override
  public String getId() {
    return id;
  }
}
