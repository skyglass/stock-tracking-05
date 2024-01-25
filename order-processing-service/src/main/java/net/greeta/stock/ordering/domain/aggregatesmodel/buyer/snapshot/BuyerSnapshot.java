package net.greeta.stock.ordering.domain.aggregatesmodel.buyer.snapshot;

import net.greeta.stock.ordering.domain.base.Snapshot;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class BuyerSnapshot implements Snapshot {
  private final String id;
  private final String userId;
  private final String buyerName;
  private final List<PaymentMethodSnapshot> paymentMethods;
}
