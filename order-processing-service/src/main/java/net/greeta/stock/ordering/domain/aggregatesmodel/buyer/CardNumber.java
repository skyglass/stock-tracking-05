package net.greeta.stock.ordering.domain.aggregatesmodel.buyer;

import net.greeta.stock.ordering.domain.exceptions.OrderingDomainException;
import net.greeta.stock.ordering.domain.base.ValueObject;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.isEmpty;

@Getter
@ToString
public class CardNumber extends ValueObject {
  private final String value;

  private CardNumber(String value) {
    if (isEmpty(value)) {
      throw new OrderingDomainException("Card number cannot be empty");
    }

    this.value = value;
  }

  public static CardNumber of(String value) {
    return new CardNumber(value);
  }

  @Override
  protected List<Object> getEqualityComponents() {
    return List.of(value);
  }
}
