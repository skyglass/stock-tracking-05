package net.greeta.stock.catalog.application.commands.changeprice;

import lombok.RequiredArgsConstructor;
import net.greeta.stock.catalog.application.commandbus.CatalogCommandHandler;
import net.greeta.stock.catalog.application.integrationevents.IntegrationEventPublisher;
import net.greeta.stock.catalog.application.integrationevents.events.ProductPriceChangedIntegrationEvent;
import net.greeta.stock.catalog.config.KafkaTopics;
import net.greeta.stock.catalog.domain.catalogitem.CatalogItemAggregateRepository;
import net.greeta.stock.catalog.domain.catalogitem.Price;
import net.greeta.stock.common.domain.dto.catalog.CatalogItemResponse;
import net.greeta.stock.shared.rest.error.NotFoundException;
import org.axonframework.commandhandling.CommandHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
@Component
public class ChangePriceCommandHandler implements CatalogCommandHandler<CatalogItemResponse, ChangePriceCommand> {
  private final IntegrationEventPublisher integrationEventPublisher;
  private final CatalogItemAggregateRepository catalogItemRepository;
  private final KafkaTopics topics;

  @CommandHandler
  @Override
  public CatalogItemResponse handle(ChangePriceCommand command) {
    final var catalogItemAggregate = catalogItemRepository.loadAggregate(command.productId());

    if (isNull(catalogItemAggregate)) {
      throw new NotFoundException("Catalog item not found");
    }

    final var catalogItem = catalogItemAggregate.invoke(Function.identity());
    final var price = Price.of(command.price());
    var priceChangedEvent = new ProductPriceChangedIntegrationEvent(
        catalogItem.getId(),
        price.getValue(),
        catalogItem.getPrice().getValue()
    );

    catalogItemAggregate.execute(c -> c.changePrice(price));

    // TODO publish PriceChangedEvent in a domain event handler
    integrationEventPublisher.publish(topics.getProductPriceChanges(), priceChangedEvent);

    return CatalogItemResponse.builder()
        .productId(command.productId())
        .version(catalogItemAggregate.version())
        .build();
  }
}
