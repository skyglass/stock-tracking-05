package net.greeta.stock.basket.builder;

import net.greeta.stock.common.GenericBuilder;
import net.greeta.stock.common.domain.dto.basket.BasketCheckout;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BasketCheckoutBuilder extends GenericBuilder<BasketCheckout> {

    public BasketCheckout build(String buyer) {
        return new BasketCheckout(buyer, UUID.randomUUID());
    }
}
