package net.greeta.stock.basket.builder;

import net.greeta.stock.common.GenericBuilder;
import net.greeta.stock.common.domain.dto.basket.BasketItem;
import net.greeta.stock.common.domain.dto.basket.CustomerBasket;
import org.springframework.stereotype.Component;

@Component
public class CustomerBasketBuilder extends GenericBuilder<CustomerBasket> {

    public CustomerBasket build(String customerId, BasketItem basketItem) {
        CustomerBasket result = new CustomerBasket(customerId);
        result.getItems().add(basketItem);
        return result;
    }
}
