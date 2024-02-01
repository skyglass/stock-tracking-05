package net.greeta.stock.common.domain.dto.order;

import lombok.Builder;
import lombok.Getter;
import net.greeta.stock.common.domain.dto.order.base.ValueObject;
import net.greeta.stock.common.domain.dto.order.buyer.*;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
public class NewOrderData extends ValueObject {
  private final UserId userId;
  private final BuyerName buyerName;
  private final UUID requestId;

  @Builder
  private NewOrderData(
      @NonNull UserId userId,
      @NonNull BuyerName buyerName,
      @NonNull UUID requestId

  ) {
    this.userId = Objects.requireNonNull(userId, "User id cannot be null");
    this.buyerName = Objects.requireNonNull(buyerName, "Buyer name cannot be null");
    this.requestId = Objects.requireNonNull(requestId, "Request id cannot be null");
  }

  @Override
  protected List<Object> getEqualityComponents() {
    return List.of(userId, buyerName);
  }
}
