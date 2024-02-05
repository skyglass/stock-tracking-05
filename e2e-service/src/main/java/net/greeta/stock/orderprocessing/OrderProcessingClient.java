package net.greeta.stock.orderprocessing;

import jakarta.validation.Valid;
import net.greeta.stock.common.domain.dto.order.CreateOrderDraftCommand;
import net.greeta.stock.common.domain.dto.order.OrderDraftDTO;
import net.greeta.stock.common.domain.dto.order.OrderViewModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "order-processing")
public interface OrderProcessingClient {

    @RequestMapping("user")
    public List<OrderViewModel.Order> getUserOrders();

    @RequestMapping("request-id/{requestId}")
    public OrderViewModel.Order getOrderByRequestId(@PathVariable String requestId);

    @RequestMapping(value = "draft", method = RequestMethod.POST)
    public OrderDraftDTO createOrderDraftFromBasketData(
            @RequestBody @Valid CreateOrderDraftCommand command
    );

}
