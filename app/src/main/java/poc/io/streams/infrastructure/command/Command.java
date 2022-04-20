package poc.io.streams.infrastructure.command;

import io.micronaut.core.annotation.Introspected;

@Introspected
public interface Command {
  String getId();
}
