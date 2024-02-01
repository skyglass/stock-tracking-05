package net.greeta.stock.catalogquery.infrastructure;

import lombok.RequiredArgsConstructor;
import net.greeta.stock.catalogquery.application.queries.catalogitembyid.CatalogItemByIdQuery;
import net.greeta.stock.catalogquery.application.querybus.QueryBus;
import net.greeta.stock.catalogquery.application.service.CatalogItemService;
import net.greeta.stock.common.domain.dto.catalog.CatalogItemDto;
import net.greeta.stock.catalogquery.model.mapper.CatalogItemMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class SpringCatalogItemService implements CatalogItemService {

    private final QueryBus queryBus;

    private final CatalogItemMapper catalogItemMapper;

    public Optional<CatalogItemDto> findById(UUID id) {
        return queryBus.execute(new CatalogItemByIdQuery(id)).map(
                        ci -> catalogItemMapper.toCatalogItemDto(ci)
                );
    }
}
