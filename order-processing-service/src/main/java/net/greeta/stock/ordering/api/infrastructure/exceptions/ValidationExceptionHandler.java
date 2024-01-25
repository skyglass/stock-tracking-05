package net.greeta.stock.ordering.api.infrastructure.exceptions;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

import static java.util.stream.Collectors.toMap;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ValidationExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ConstraintViolationException.class)
  protected ResponseEntity<Map<Path, String>> handleConstraintViolationException(
      ConstraintViolationException ex, WebRequest request) {
    var errors = ex.getConstraintViolations()
        .stream()
        .collect(toMap(ConstraintViolation::getPropertyPath, ConstraintViolation::getMessage));
    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request
  ) {
    var errors = ex
        .getBindingResult()
        .getFieldErrors()
        .stream()
        .collect(toMap(FieldError::getField, FieldError::getDefaultMessage));
    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }
}
