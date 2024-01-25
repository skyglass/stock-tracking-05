package net.greeta.stock.common.domain.dto.catalog;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AddBrandResponse {
  private final String brandName;
}
