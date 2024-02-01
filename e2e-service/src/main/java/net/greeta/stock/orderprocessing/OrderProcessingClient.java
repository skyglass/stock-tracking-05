package net.greeta.stock.orderprocessing;

import net.greeta.stock.common.domain.dto.order.OrderViewModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name = "order-processing")
public interface OrderProcessingClient {

    @RequestMapping("user")
    public List<OrderViewModel.Order> getUserOrders();

    @RequestMapping("request-id/{requestId}")
    public OrderViewModel.Order getOrderByRequestId(@PathVariable String requestId);

}
