package app;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

import java.net.URI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LoadBalancerTest {

  private final static URI uriAddress = URI.create("127.0.0.1");
  private final static Address ADDRESS = new Address(uriAddress);

  @Mock
  private AddressRegistry addressRegistry;

  private LoadBalancer underTest;

  @BeforeEach
  void setUp() {
    underTest = new LoadBalancer(10, addressRegistry);
  }

  @Test
  void should_Register_When_AddressIsNotUsed() {
    // When
    var actual = underTest.register(ADDRESS);

    // Then
    assertTrue(actual);
  }

  @Test
  void should_ThrowException_When_AddressAlreadyInUse() {
    // Given
    given(addressRegistry.hasAddress(ADDRESS)).willReturn(true);

    // When -> Then
    assertThrowsExactly(AddressAlreadyInUseException.class, () -> underTest.register(ADDRESS));
  }

  @Test
  void should_ThrowException_When_NotEnoughAddressSlots() {
    // Given
    final var loadBalancer = new LoadBalancer(1, addressRegistry);
    loadBalancer.register(ADDRESS);
    final var anotherAddress = new Address(URI.create("localhost"));

    // When -> Then
    assertThrowsExactly(NumberOutOfBoundException.class, () -> loadBalancer.register(anotherAddress));
  }

}
