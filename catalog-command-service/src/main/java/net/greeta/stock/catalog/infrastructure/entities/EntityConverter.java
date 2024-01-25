package net.greeta.stock.catalog.infrastructure.entities;

interface EntityConverter<E, D> {
  E toEntity(D domainObject);

  D fromEntity(E entity);
}
