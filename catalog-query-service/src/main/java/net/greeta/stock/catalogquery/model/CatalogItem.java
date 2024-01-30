package net.greeta.stock.catalogquery.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "catalog_item")
public class CatalogItem extends DbEntity {

  @Column(name = "name", length = 50, nullable = false)
  private String name;

  @Column(name = "description")
  private String description;

  @Column(name = "price", nullable = false)
  private Double price;

  @Column(name = "picture_file_name")
  private String pictureFileName;

  // Quantity in stock
  @Column(name = "available_stock")
  private Integer availableStock;

}

