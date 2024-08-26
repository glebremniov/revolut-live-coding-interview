package app;

public class AddressAlreadyInUseException extends RuntimeException {

  private static final String ERROR_MESSAGE = "Address already in use: %s";

  public AddressAlreadyInUseException(Address address) {
    super(ERROR_MESSAGE.formatted(address.uriAddress()));
  }
}
