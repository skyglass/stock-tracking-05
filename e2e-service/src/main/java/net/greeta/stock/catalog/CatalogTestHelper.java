package net.greeta.stock.catalog;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.greeta.stock.basket.BasketClient;
import net.greeta.stock.catalog.builder.CreateProductCommandBuilder;
import net.greeta.stock.catalogcommand.CatalogCommandClient;
import net.greeta.stock.catalogquery.CatalogQueryClient;
import net.greeta.stock.common.GenericTestHelper;
import net.greeta.stock.common.domain.dto.catalog.CatalogItemResponse;
import net.greeta.stock.common.domain.dto.catalog.CreateProductCommand;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CatalogTestHelper extends GenericTestHelper {

    private final CatalogCommandClient catalogCommandClient;

    private final CatalogQueryClient catalogQueryClient;

    private final CreateProductCommandBuilder createProductCommandBuilder;

    public CatalogItemResponse createProduct(String name, Double price, Integer availableStock) {
        return catalogCommandClient.createProduct(
                createProductCommandBuilder.build(name, price, availableStock)
        );
    }



}