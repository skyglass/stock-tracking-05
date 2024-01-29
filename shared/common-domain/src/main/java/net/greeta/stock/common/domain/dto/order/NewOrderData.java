package net.greeta.stock.common.domain.dto.order;

import lombok.Builder;
import lombok.Getter;
import net.greeta.stock.common.domain.dto.order.base.ValueObject;
import net.greeta.stock.common.domain.dto.order.buyer.*;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Objects;

@Getter
public class NewOrderData extends ValueObject {
  private final UserId userId;
  private final BuyerName buyerName;

  @Builder
  private NewOrderData(
      @NonNull UserId userId,
      @NonNull BuyerName buyerName
  ) {
    this.userId = Objects.requireNonNull(userId, "User id cannot be null");
    this.buyerName = Objects.requireNonNull(buyerName, "Buyer name cannot be null");
  }

  @Override
  protected List<Object> getEqualityComponents() {
    return List.of(userId, buyerName);
  }
}
