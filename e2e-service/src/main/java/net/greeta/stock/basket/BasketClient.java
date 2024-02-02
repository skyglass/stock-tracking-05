package net.greeta.stock.basket;

import jakarta.validation.Valid;
import net.greeta.stock.common.domain.dto.basket.BasketCheckout;
import net.greeta.stock.common.domain.dto.basket.CustomerBasket;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name = "basket")
public interface BasketClient {

    @RequestMapping(path = "direct-checkout", method = RequestMethod.POST)
    public void directCheckout(
            @RequestBody @Valid CustomerBasket basket,
            @RequestHeader("x-requestid") UUID requestId
    );

}