package net.greeta.stock.ordering.infrastructure.entity;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@MappedSuperclass
abstract class DbEntity {
  @Id
  @Getter
  protected UUID id;
}
