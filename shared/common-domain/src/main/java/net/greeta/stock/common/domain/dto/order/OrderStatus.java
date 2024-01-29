package net.greeta.stock.common.domain.dto.order;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.greeta.stock.common.domain.dto.order.exceptions.OrderingDomainException;
import org.springframework.lang.NonNull;

import java.util.stream.Stream;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum OrderStatus {
  Submitted("Submitted"),
  AwaitingValidation("AwaitingValidation"),
  StockConfirmed("StockConfirmed"),
  Paid("Paid"),
  Shipped("Shipped"),
  Cancelled("Cancelled");

  @Getter
  private final String status;

  public static OrderStatus of(@NonNull String status) {
    return Stream.of(values()).filter(s -> s.getStatus().equals(status))
        .findFirst()
        .orElseThrow(() -> new OrderingDomainException("Invalid name for OrderStatus: %s".formatted(status)));
  }

}
