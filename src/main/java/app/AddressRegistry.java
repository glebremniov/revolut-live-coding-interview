package app;

import java.net.URI;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class AddressRegistry {

  private final Map<URI, Address> registry = new ConcurrentHashMap<>();

  public boolean hasAddress(final Address address) {
    Objects.requireNonNull(address, "Address must be present");
    return registry.containsKey(address.uriAddress());
  }

  public void register(final Address address) {
    Objects.requireNonNull(address, "Address must be present");
    registry.put(address.uriAddress(), address);
  }

  public void flush() {
    registry.clear();
  }

}
