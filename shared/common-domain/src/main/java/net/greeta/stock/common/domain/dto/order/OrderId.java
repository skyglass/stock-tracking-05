package net.greeta.stock.common.domain.dto.order;

import lombok.ToString;
import net.greeta.stock.common.domain.dto.order.base.Identifier;
import org.springframework.lang.NonNull;

import java.util.UUID;

@ToString
public class OrderId extends Identifier {
  private OrderId(UUID uuid) {
    super(uuid);
  }

  public static OrderId of(@NonNull UUID value) {
    return new OrderId(value);
  }

  public static OrderId of(@NonNull String value) {
    return new OrderId(UUID.fromString(value));
  }

}
