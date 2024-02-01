package net.greeta.stock.common.domain.dto.order.snapshot;

import lombok.Builder;
import lombok.Getter;
import net.greeta.stock.common.domain.dto.order.base.Snapshot;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
public class OrderSnapshot implements Snapshot {
  private final String id;
  private final LocalDateTime orderDate;
  private final String description;
  private final boolean draft;
  private final String orderStatus;
  private final String buyerId;
  private final List<OrderItemSnapshot> orderItems;
  private final UUID requestId;
}
