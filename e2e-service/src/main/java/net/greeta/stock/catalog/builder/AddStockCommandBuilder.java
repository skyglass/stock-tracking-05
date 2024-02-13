package net.greeta.stock.catalog.builder;

import net.greeta.stock.common.GenericBuilder;
import net.greeta.stock.common.domain.dto.catalog.AddStockDto;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AddStockCommandBuilder extends GenericBuilder<AddStockDto> {

    public AddStockDto build(UUID productId, Integer quantity) {
        return new AddStockDto(productId, quantity);
    }


}
