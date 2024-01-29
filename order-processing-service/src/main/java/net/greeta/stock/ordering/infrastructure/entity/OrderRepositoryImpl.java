package net.greeta.stock.ordering.infrastructure.entity;

import net.greeta.stock.common.domain.dto.order.Order;
import net.greeta.stock.common.domain.dto.order.OrderId;
import net.greeta.stock.ordering.domain.aggregatesmodel.order.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
class OrderRepositoryImpl implements OrderRepository {
  private final OrderEntityConverter orderEntityConverter;
  private final ApplicationEventPublisher applicationEventPublisher;

  @PersistenceContext
  private final EntityManager entityManager;

  @Override
  public Order save(@NonNull Order aggregateRoot) {
    var orderEntity = orderEntityConverter.toEntity(aggregateRoot);
    entityManager.merge(orderEntity);

    aggregateRoot.domainEvents().forEach(applicationEventPublisher::publishEvent);
    aggregateRoot.clearDomainEvents();

    return orderEntityConverter.fromEntity(orderEntity);
  }

  @Override
  public Optional<Order> findById(@NonNull OrderId id) {
    return Optional.ofNullable(entityManager.find(OrderEntity.class, id.getValue()))
        .map(orderEntityConverter::fromEntity);
  }
}
