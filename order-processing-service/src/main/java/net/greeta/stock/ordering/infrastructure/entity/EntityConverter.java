package net.greeta.stock.ordering.infrastructure.entity;

import net.greeta.stock.common.domain.dto.order.base.Entity;

interface EntityConverter<E extends DbEntity, D extends Entity<?>> {
  E toEntity(D domainEntity);

  D fromEntity(E entity);
}
