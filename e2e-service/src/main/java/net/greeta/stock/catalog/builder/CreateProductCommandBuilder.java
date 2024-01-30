package net.greeta.stock.catalog.builder;

import net.greeta.stock.common.GenericBuilder;
import net.greeta.stock.common.domain.dto.catalog.CreateProductCommand;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CreateProductCommandBuilder extends GenericBuilder<CreateProductCommand> {

    public CreateProductCommand build(String name, Double price, Integer availableStock) {
        return new CreateProductCommand(name, name, price, null, availableStock);
    }

    //Just Example
    public CreateProductCommand build(String name, Double price, Integer availableStock, String description) {
        return apply(build(name, price, availableStock),
                c -> new CreateProductCommand(
                        c.name(), description, c.price(),
                        c.pictureFileName(), c.availableStock()));
    }

    //Just example
    public CreateProductCommand build(String name, Double price, Integer availableStock,
                                      Function<CreateProductCommand, CreateProductCommand> function) {
        return apply(build(name, price, availableStock), function);
    }


}
