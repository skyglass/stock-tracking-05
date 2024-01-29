package net.greeta.stock.common.domain.dto.order;

import java.util.UUID;

public record OrderItemDTO(
    UUID productId,
    String productName,
    Double unitPrice,
    Double discount,
    Integer units,
    String pictureUrl
) {
}
