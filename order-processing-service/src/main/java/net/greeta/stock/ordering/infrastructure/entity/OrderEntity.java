package net.greeta.stock.ordering.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
@Getter
@Entity
@Table(name = "orders")
class OrderEntity extends DbEntity {
  @Column(nullable = false)
  private LocalDateTime orderDate;

  @Column(name = "buyer_id", nullable = false)
  private UUID buyerId;

  @Column(name = "order_status", nullable = false)
  private String orderStatus;

  @Column(name = "request_id", nullable = false)
  private UUID requestId;

  @Column(name = "description")
  private String description;

  @Column(name = "is_draft")
  private boolean isDraft;

  @Setter
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
  private Set<OrderItemEntity> orderItems;
}
