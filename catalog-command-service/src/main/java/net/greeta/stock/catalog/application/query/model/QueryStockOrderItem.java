package net.greeta.stock.catalog.application.query.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import net.greeta.stock.catalog.domain.stockorder.StockOrderItemStatus;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "stock_order_item")
public class QueryStockOrderItem extends DbEntity {

  @Column(nullable = false)
  private UUID orderId;

  @Column(nullable = false)
  private UUID productId;

  @Column(nullable = false)
  private Integer quantity;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private StockOrderItemStatus stockOrderItemStatus;

}

