package net.greeta.stock.ordering.domain.aggregatesmodel.buyer.snapshot;

import net.greeta.stock.ordering.domain.base.Snapshot;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class PaymentMethodSnapshot implements Snapshot {
  private final String id;
  private final String alias;
  private final String cardNumber;
  private final String securityNumber;
  private final String cardHolderName;
  private final LocalDate expiration;
  private final String cardType;
}
