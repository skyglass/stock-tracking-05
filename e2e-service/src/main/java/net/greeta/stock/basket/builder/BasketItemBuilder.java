package net.greeta.stock.basket.builder;

import net.greeta.stock.common.GenericBuilder;
import net.greeta.stock.common.domain.dto.basket.BasketItem;
import net.greeta.stock.common.domain.dto.catalog.CreateProductCommand;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.function.Consumer;

@Component
public class BasketItemBuilder extends GenericBuilder<BasketItem> {

    public BasketItem build(UUID productId, String productName,
                            Double unitPrice, Integer quantity) {
        BasketItem result = new BasketItem();
        result.setProductId(productId);
        result.setProductName(productName);
        result.setUnitPrice(unitPrice);
        result.setQuantity(quantity);
        return result;
    }

    //Just example
    public BasketItem build(UUID productId, String productName,
                            Double unitPrice, Integer quantity,
                            String pictureUrl) {
        return build(build(productId, productName, unitPrice, quantity),
                c -> {
                    c.setPictureUrl(pictureUrl);
                });
    }


    //Just example
    public BasketItem build(UUID productId, String productName,
                            Double unitPrice, Integer quantity,
                            Consumer<BasketItem> consumer) {
        return build(build(productId, productName, unitPrice, quantity),
                consumer);
    }
}
