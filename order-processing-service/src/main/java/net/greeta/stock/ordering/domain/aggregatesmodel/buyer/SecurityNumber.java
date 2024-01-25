package net.greeta.stock.ordering.domain.aggregatesmodel.buyer;

import net.greeta.stock.ordering.domain.exceptions.OrderingDomainException;
import net.greeta.stock.ordering.domain.base.ValueObject;
import lombok.Getter;
import lombok.ToString;
import org.springframework.lang.NonNull;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.isEmpty;

@Getter
@ToString
public class SecurityNumber extends ValueObject {
  private final String value;

  private SecurityNumber(@NonNull String value) {
    if (isEmpty(value)) {
      throw new OrderingDomainException("Security number cannot be empty");
    }

    this.value = value;
  }

  public static SecurityNumber of(@NonNull String value) {
    return new SecurityNumber(value);
  }

  @Override
  protected List<Object> getEqualityComponents() {
    return List.of(value);
  }
}
