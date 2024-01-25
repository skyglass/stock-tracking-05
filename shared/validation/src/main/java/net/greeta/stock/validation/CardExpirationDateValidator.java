package net.greeta.stock.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

import static java.util.Objects.nonNull;

public class CardExpirationDateValidator implements ConstraintValidator<CardExpirationDate, LocalDate> {

  @Override
  public boolean isValid(LocalDate expiration, ConstraintValidatorContext constraintValidatorContext) {
    return nonNull(expiration) && expiration.isAfter(LocalDate.now());
  }
}
