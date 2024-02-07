package net.greeta.stock.shared.eventhandling.events;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class CatalogItemCreated extends Event {
  private String name;
  private String description;
  private Double price;
  private String pictureFileName;
  private Integer availableStock;

  public CatalogItemCreated(
      UUID id,
      String name,
      String description,
      Double price,
      String pictureFileName,
      Integer availableStock
  ) {
    super(id);
    this.name = name;
    this.description = description;
    this.price = price;
    this.pictureFileName = pictureFileName;
    this.availableStock = availableStock;
  }
}
