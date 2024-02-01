package net.greeta.stock.catalogquery.api;

import lombok.RequiredArgsConstructor;
import net.greeta.stock.catalogquery.application.queries.catalogitems.CatalogItemsQuery;
import net.greeta.stock.catalogquery.application.queries.catalogitemsbyids.CatalogItemsByIdsQuery;
import net.greeta.stock.catalogquery.application.queries.catalogitemswithname.CatalogItemWithNameQuery;
import net.greeta.stock.catalogquery.application.querybus.QueryBus;
import net.greeta.stock.catalogquery.application.service.CatalogItemService;
import net.greeta.stock.catalogquery.model.CatalogItem;
import net.greeta.stock.common.domain.dto.catalog.CatalogItemDto;
import net.greeta.stock.shared.rest.error.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.apache.logging.log4j.util.Strings.isEmpty;

/**
 * Handle requests for catalog service.
 */
@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class CatalogController {
  private static final Logger logger = LoggerFactory.getLogger(CatalogController.class);

  private final QueryBus queryBus;

  private final CatalogItemService catalogItemService;

  /**
   * Returns catalog items for given item ids.
   *
   * @param ids catalog item ids
   * @return catalog items
   */
  @RequestMapping("items/withids/{ids}")
  public Iterable<CatalogItem> catalogItemsByIds(@PathVariable String ids) {
    if (isEmpty(ids)) {
      throw new BadRequestException("Invalid ids value");
    }

    final var itemIds = Arrays.
      stream(ids.split(","))
      .map(UUID::fromString)
      .collect(Collectors.toSet());
    return queryBus.execute(new CatalogItemsByIdsQuery(itemIds));
  }

  /**
   * Returns catalog items in the given page.
   *
   * @param pageSize   number of items
   * @param pageIndex  page
   * @return catalog items
   */
  @RequestMapping("items")
  public Page<CatalogItem> catalogItems(
    @RequestParam(defaultValue = "10", required = false) Integer pageSize,
    @RequestParam(defaultValue = "0", required = false) Integer pageIndex
  ) {
    logger.info("Find catalog items - page size: {}, page index: {}", pageSize, pageIndex);

    return queryBus.execute(new CatalogItemsQuery(pageSize, pageIndex));
  }

  /**
   * Returns catalog item by given id.
   *
   * @param id item id
   * @return catalog item
   */
  @RequestMapping("items/{id}")
  public ResponseEntity<CatalogItemDto> catalogItem(@PathVariable UUID id) {
    logger.info("Find catalog item: {}", id);
    return ResponseEntity.of(catalogItemService.findById(id));
  }

  /**
   * Returns catalog items by given name in give page.
   *
   * @param pageSize  number of items to be returned
   * @param pageIndex page
   * @param name      name of item
   * @return catalog items
   */
  @RequestMapping("items/withname/{name}")
  public Page<CatalogItem> catalogItems(
    @RequestParam(defaultValue = "10", required = false) Integer pageSize,
    @RequestParam(defaultValue = "0", required = false) Integer pageIndex,
    @PathVariable String name
  ) {
    if (isEmpty(name)) {
      throw new BadRequestException("The name must be at least one character long");
    }
    return queryBus.execute(new CatalogItemWithNameQuery(pageSize, pageIndex, name));
  }

}

