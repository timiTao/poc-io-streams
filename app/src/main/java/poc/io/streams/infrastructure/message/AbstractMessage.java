package poc.io.streams.infrastructure.message;

public abstract class AbstractMessage implements Message {
  private final String id;
  private final String type;

  protected AbstractMessage(String id, String type) {
    this.id = id;
    this.type = type;
  }

  @Override
  public String id() {
    return id;
  }

  public String type() {
    return type;
  }
}
