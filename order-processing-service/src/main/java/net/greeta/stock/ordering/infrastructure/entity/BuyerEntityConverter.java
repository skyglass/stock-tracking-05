package net.greeta.stock.ordering.infrastructure.entity;

import net.greeta.stock.common.domain.dto.order.buyer.snapshot.BuyerSnapshot;
import net.greeta.stock.common.domain.dto.order.buyer.Buyer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
class BuyerEntityConverter implements EntityConverter<BuyerEntity, Buyer> {
  @Override
  public BuyerEntity toEntity(Buyer buyer) {
    var snapshot = buyer.snapshot();
    var buyerEntity = BuyerEntity.builder()
        .id(UUID.fromString(snapshot.getId()))
        .userId(snapshot.getUserId())
        .name(snapshot.getBuyerName())
        .build();

    return buyerEntity;
  }

  @Override
  public Buyer fromEntity(BuyerEntity entity) {
    return Buyer.rehydrate(BuyerSnapshot.builder()
        .id(entity.getId().toString())
        .userId(entity.getUserId())
        .buyerName(entity.getName())
        .build());
  }

}
