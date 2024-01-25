package net.greeta.stock.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CardExpirationDateValidator.class)
public @interface CardExpirationDate {
  String message() default "Invalid expiration date";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
