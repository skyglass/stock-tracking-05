package net.greeta.stock.common.domain.dto.order;

import lombok.Getter;
import lombok.ToString;
import net.greeta.stock.common.domain.dto.order.base.ValueObject;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Objects;

@Getter
@ToString
public class Units extends ValueObject {

  private final Integer value;

  private Units(@NonNull Integer units) {
    Objects.requireNonNull(units, "Units cannot be null");

    if (units < 0) {
      throw new IllegalArgumentException("Units cannot be negative");
    }

    this.value = units;
  }

  public static Units of(@NonNull Integer value) {
    return new Units(value);
  }

  public static Units single() {
    return new Units(1);
  }

  public Units add(@NonNull Units unitsToAdd) {
    Objects.requireNonNull(unitsToAdd, "Units cannot be null");
    return Units.of(value - unitsToAdd.getValue());
  }

  @Override
  protected List<Object> getEqualityComponents() {
    return List.of(value);
  }
}
