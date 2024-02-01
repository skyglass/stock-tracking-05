package net.greeta.stock.catalogquery;

import net.greeta.stock.common.domain.dto.catalog.CatalogItemDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@FeignClient(name = "catalog-query")
public interface CatalogQueryClient {

    @RequestMapping("items/{id}")
    public CatalogItemDto catalogItem(@PathVariable UUID id);


}