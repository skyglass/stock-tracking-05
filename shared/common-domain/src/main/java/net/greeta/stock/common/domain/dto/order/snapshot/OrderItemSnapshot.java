package net.greeta.stock.common.domain.dto.order.snapshot;

import lombok.Builder;
import lombok.Getter;
import net.greeta.stock.common.domain.dto.order.base.Snapshot;

import java.util.UUID;

@Builder
@Getter
public class OrderItemSnapshot implements Snapshot {
  private final String id;
  private final String productName;
  private final Double discount;
  private final String pictureUrl;
  private final Double unitPrice;
  private final Integer units;
  private final UUID productId;
}
