package net.greeta.stock.common.domain.dto.catalog;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class CatalogItemResponse {
  private final UUID productId;
  private final Long version;
}
