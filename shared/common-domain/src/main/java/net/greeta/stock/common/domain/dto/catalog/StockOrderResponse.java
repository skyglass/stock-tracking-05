package net.greeta.stock.common.domain.dto.catalog;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class StockOrderResponse {
    private final UUID orderId;
    private final Long version;
}
