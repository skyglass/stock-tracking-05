package net.greeta.stock.common.domain.dto.order.buyer;

import net.greeta.stock.common.domain.dto.order.base.Identifier;
import lombok.ToString;
import org.springframework.lang.NonNull;

import java.util.UUID;

@ToString
public class BuyerId extends Identifier {
  private BuyerId(UUID value) {
    super(value);
  }

  public static BuyerId of(@NonNull UUID value) {
    return new BuyerId(value);
  }

  public static BuyerId of(@NonNull String value) {
    return new BuyerId(UUID.fromString(value));
  }

}
