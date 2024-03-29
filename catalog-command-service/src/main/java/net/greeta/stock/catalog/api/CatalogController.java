package net.greeta.stock.catalog.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.greeta.stock.catalog.application.commandbus.CatalogCommandBus;
import net.greeta.stock.catalog.application.commandbus.CommandBusRetryHelper;
import net.greeta.stock.catalog.application.commands.changeprice.ChangePriceCommand;
import net.greeta.stock.catalog.application.commands.changeproductname.ChangeProductNameCommand;
import net.greeta.stock.catalog.application.commands.removestock.RemoveStockCommand;
import net.greeta.stock.catalog.domain.mapper.AddStockMapper;
import net.greeta.stock.common.domain.dto.catalog.AddStockDto;
import net.greeta.stock.common.domain.dto.catalog.CatalogItemResponse;
import net.greeta.stock.common.domain.dto.catalog.CreateProductCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Handle requests for catalog service.
 */
@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class CatalogController {
  private static final Logger logger = LoggerFactory.getLogger(CatalogController.class);

  private final CatalogCommandBus commandBus;

  private final CommandBusRetryHelper commandBusRetryHelper;

  private final AddStockMapper addStockMapper;

  @RequestMapping(method = RequestMethod.POST, path = "items")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<CatalogItemResponse> createProduct(@RequestBody @Valid CreateProductCommand command) {
    logger.info("Create product requested");
    final var response = commandBus.execute(command);
    return ResponseEntity.ok(response);
  }

  @RequestMapping(method = RequestMethod.PUT, path = "items/changeprice")
  public ResponseEntity<CatalogItemResponse> changeProductPrice(@RequestBody @Valid ChangePriceCommand command) {
    logger.info("Change price request for product: {}", command.productId());
    final var response = commandBus.execute(command);
    return ResponseEntity.ok(response);
  }

  @RequestMapping(method = RequestMethod.PUT, path = "items/addstock")
  public ResponseEntity<CatalogItemResponse> addStock(@RequestBody @Valid AddStockDto dto) {
    logger.info("Add stock request for product: {}", dto.productId());
    final var response = commandBusRetryHelper.execute(addStockMapper.toCommand(dto));
    return ResponseEntity.ok(response);
  }

  @RequestMapping(method = RequestMethod.PUT, path = "items/removestock")
  public ResponseEntity<CatalogItemResponse> removeStock(@RequestBody @Valid RemoveStockCommand command) {
    logger.info("Remove stock request for product: {}", command.getProductId());
    final var response = commandBus.execute(command);
    return ResponseEntity.ok(response);
  }

  @RequestMapping(method = RequestMethod.PUT, path = "items/changeproductname")
  public ResponseEntity<CatalogItemResponse> changeProductName(@RequestBody @Valid ChangeProductNameCommand command) {
    logger.info("Change product name request for product: {}", command.productId());
    final var response = commandBus.execute(command);
    return ResponseEntity.ok(response);
  }

}
