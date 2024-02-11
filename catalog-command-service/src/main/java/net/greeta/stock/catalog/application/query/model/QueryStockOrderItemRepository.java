package net.greeta.stock.catalog.application.query.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QueryStockOrderItemRepository extends JpaRepository<QueryStockOrderItem, UUID> {
  Optional<List<QueryStockOrderItem>> findAllByProductIdAndStockOrderItemStatus(UUID productId, StockOrderItemStatus stockOrderItemStatus);

  Optional<QueryStockOrderItem> findByOrderIdAndProductId(UUID orderId, UUID productId);
}
