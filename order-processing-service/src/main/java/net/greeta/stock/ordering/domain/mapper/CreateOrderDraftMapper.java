package net.greeta.stock.ordering.domain.mapper;

import net.greeta.stock.common.domain.dto.order.CreateOrderDraftDto;
import net.greeta.stock.ordering.api.application.commands.CreateOrderDraftCommand;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public abstract class CreateOrderDraftMapper {

    public abstract CreateOrderDraftCommand toCommand(CreateOrderDraftDto createOrderDraftDto);

}
