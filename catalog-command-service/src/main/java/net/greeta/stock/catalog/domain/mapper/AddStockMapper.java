package net.greeta.stock.catalog.domain.mapper;

import net.greeta.stock.catalog.application.commands.addstock.AddStockCommand;
import net.greeta.stock.common.domain.dto.catalog.AddStockDto;
import net.greeta.stock.common.domain.dto.order.CreateOrderDraftDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public abstract class AddStockMapper {

    public abstract AddStockCommand toCommand(AddStockDto addStockDto);

}
