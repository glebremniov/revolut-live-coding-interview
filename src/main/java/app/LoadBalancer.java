package app;

import java.util.concurrent.atomic.AtomicInteger;

public class LoadBalancer {

  private final AddressRegistry addressRegistry;
  private final AtomicInteger activeConnectionCounter;
  private final int connectionCapacity;

  public LoadBalancer(
      final int connectionCapacity,
      final AddressRegistry addressRegistry
  ) {
    this.connectionCapacity = connectionCapacity;
    this.addressRegistry = addressRegistry;
    activeConnectionCounter = new AtomicInteger();
  }

  public boolean register(Address address) throws AddressAlreadyInUseException, NumberOutOfBoundException {
    final var currentConnectionNumber = activeConnectionCounter.incrementAndGet();

    if (connectionCapacity < currentConnectionNumber) {
      throw new NumberOutOfBoundException();
    }

    if (addressRegistry.hasAddress(address)) {
      throw new AddressAlreadyInUseException(address);
    }

    addressRegistry.register(address);

    return true;
  }

}
