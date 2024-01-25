package net.greeta.stock.payment.infrastructure;

import net.greeta.stock.shared.rest.error.ControllerExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerExceptionHandler extends ControllerExceptionHandler {
}
