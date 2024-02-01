package net.greeta.stock.catalogquery.application.service;

import net.greeta.stock.common.domain.dto.catalog.CatalogItemDto;

import java.util.Optional;
import java.util.UUID;

public interface CatalogItemService {

    Optional<CatalogItemDto> findById(UUID id);
}
