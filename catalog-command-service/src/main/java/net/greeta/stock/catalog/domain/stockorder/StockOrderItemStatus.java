package net.greeta.stock.catalog.domain.stockorder;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.greeta.stock.common.domain.dto.order.exceptions.OrderingDomainException;
import org.springframework.lang.NonNull;

import java.util.stream.Stream;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum StockOrderItemStatus {
    AwaitingConfirmation("AwaitingConfirmation"),
    StockConfirmed("StockConfirmed");

    @Getter
    private final String status;

    public static StockOrderItemStatus of(@NonNull String status) {
        return Stream.of(values()).filter(s -> s.getStatus().equals(status))
                .findFirst()
                .orElseThrow(() -> new OrderingDomainException("Invalid name for StockOrderStatus: %s".formatted(status)));
    }

}
