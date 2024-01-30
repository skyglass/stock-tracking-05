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

    @RequestMapping("/customer/{customerId}")
    public CustomerBasket getBasketByCustomerId(@PathVariable String customerId);

    @RequestMapping(method = RequestMethod.POST)
    public CustomerBasket updateBasket(@RequestBody @Valid CustomerBasket basket);

    @RequestMapping(path = "checkout", method = RequestMethod.POST)
    public void checkout(@RequestBody @Valid BasketCheckout basketCheckout,
                         @RequestHeader("x-requestid") String requestId
    );

}