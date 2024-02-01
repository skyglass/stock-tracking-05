package net.greeta.stock.common.domain.dto.catalog;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


public record CatalogItemDto(
        UUID id,
        String name,
        String description,
        Double price,
        String pictureFileName,
        Integer availableStock
) {
}