package net.greeta.stock.catalogcommand;

import jakarta.validation.Valid;
import net.greeta.stock.common.domain.dto.catalog.AddStockDto;
import net.greeta.stock.common.domain.dto.catalog.CatalogItemResponse;
import net.greeta.stock.common.domain.dto.catalog.CreateProductCommand;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "catalog-command")
public interface CatalogCommandClient {

    @RequestMapping(method = RequestMethod.POST, path = "items")
    CatalogItemResponse createProduct(@RequestBody @Valid CreateProductCommand command);

    @RequestMapping(method = RequestMethod.PUT, path = "items/addstock")
    CatalogItemResponse addStock(@RequestBody @Valid AddStockDto command);

}
