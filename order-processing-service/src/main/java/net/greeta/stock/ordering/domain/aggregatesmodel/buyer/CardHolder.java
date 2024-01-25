package net.greeta.stock.ordering.domain.aggregatesmodel.buyer;

import net.greeta.stock.ordering.domain.exceptions.OrderingDomainException;
import net.greeta.stock.ordering.domain.base.ValueObject;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.isEmpty;

@Getter
@ToString
public class CardHolder extends ValueObject {
  private final String value;

  private CardHolder(String value) {
    if (isEmpty(value)) {
      throw new OrderingDomainException("Card holder cannot be empty");
    }

    this.value = value;
  }

  public static CardHolder of(String value) {
    return new CardHolder(value);
  }

  @Override
  protected List<Object> getEqualityComponents() {
    return List.of(value);
  }
}
