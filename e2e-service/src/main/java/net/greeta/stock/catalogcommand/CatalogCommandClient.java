package net.greeta.stock.catalogcommand;

import jakarta.validation.Valid;
import net.greeta.stock.common.domain.dto.catalog.AddStockCommand;
import net.greeta.stock.common.domain.dto.catalog.CatalogItemResponse;
import net.greeta.stock.common.domain.dto.catalog.CreateProductCommand;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

@FeignClient(name = "catalog-command")
public interface CatalogCommandClient {

    @RequestMapping(method = RequestMethod.POST, path = "items")
    CatalogItemResponse createProduct(@RequestBody @Valid CreateProductCommand command);

    @RequestMapping(method = RequestMethod.PUT, path = "items/addstock")
    CatalogItemResponse addStock(@RequestBody @Valid AddStockCommand command);

}
