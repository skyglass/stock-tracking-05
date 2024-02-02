package net.greeta.stock.catalog.builder;

import net.greeta.stock.common.GenericBuilder;
import net.greeta.stock.common.domain.dto.catalog.AddStockCommand;
import net.greeta.stock.common.domain.dto.catalog.CreateProductCommand;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.function.Function;

@Component
public class AddStockCommandBuilder extends GenericBuilder<AddStockCommand> {

    public AddStockCommand build(UUID productId, Integer quantity) {
        return new AddStockCommand(productId, quantity);
    }


}
