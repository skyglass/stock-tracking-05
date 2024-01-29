package net.greeta.stock.common.domain.dto.order.buyer.snapshot;

import net.greeta.stock.common.domain.dto.order.base.Snapshot;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class BuyerSnapshot implements Snapshot {
  private final String id;
  private final String userId;
  private final String buyerName;
}
