package net.greeta.stock.catalogquery.model.mapper;

import net.greeta.stock.catalogquery.model.CatalogItem;
import net.greeta.stock.common.domain.dto.catalog.CatalogItemDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public abstract class CatalogItemMapper {

    public abstract CatalogItemDto toCatalogItemDto(CatalogItem catalogItem);

}
