package net.greeta.stock.catalog.application.commands.addbrand;

import net.greeta.stock.catalog.application.commandbus.CatalogCommandHandler;
import net.greeta.stock.catalog.domain.catalogitem.Brand;
import net.greeta.stock.catalog.domain.catalogitem.BrandRepository;
import lombok.RequiredArgsConstructor;
import net.greeta.stock.common.domain.dto.catalog.AddBrandCommand;
import net.greeta.stock.common.domain.dto.catalog.AddBrandResponse;
import org.axonframework.commandhandling.CommandHandler;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class AddBrandCommandHandler implements CatalogCommandHandler<AddBrandResponse, AddBrandCommand> {
  private final BrandRepository brandRepository;

  @CommandHandler
  @Override
  public AddBrandResponse handle(AddBrandCommand command) {
    final var brand = brandRepository.save(Brand.of(UUID.randomUUID(), command.name()));

    return AddBrandResponse.builder()
        .brandName(brand.getName())
        .build();
  }
}
