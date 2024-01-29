package net.greeta.stock.common.domain.dto.order.buyer;

import lombok.Getter;
import net.greeta.stock.common.domain.dto.order.OrderId;
import net.greeta.stock.common.domain.dto.order.base.AggregateRoot;
import net.greeta.stock.common.domain.dto.order.buyer.snapshot.BuyerSnapshot;
import net.greeta.stock.common.domain.dto.order.events.BuyerVerifiedDomainEvent;
import org.springframework.lang.NonNull;

import java.util.Objects;
import java.util.UUID;

public class Buyer extends AggregateRoot<BuyerId> {

  @Getter
  private final UserId userId;

  @Getter
  private final BuyerName buyerName;

  private Buyer(
      @NonNull BuyerId id,
      @NonNull UserId userId,
      @NonNull BuyerName buyerName
  ) {
    this.id = Objects.requireNonNull(id, "Buyer id cannot be null");
    this.userId = Objects.requireNonNull(userId, "User id cannot be null");
    this.buyerName = Objects.requireNonNull(buyerName, "Buyer name cannot be null");
  }

  public Buyer(@NonNull UserId userId, @NonNull BuyerName buyerName) {
    this(BuyerId.of(UUID.randomUUID()), userId, buyerName);
  }

  @NonNull
  public static Buyer rehydrate(@NonNull BuyerSnapshot snapshot) {
    Objects.requireNonNull(snapshot, "Snapshot cannot be null");
    return new Buyer(
        BuyerId.of(snapshot.getId()),
        UserId.of(snapshot.getUserId()),
        BuyerName.of(snapshot.getBuyerName())
    );
  }

  @NonNull
  public void verify(@NonNull OrderId orderId) {
    Objects.requireNonNull(orderId, "Order id cannot be null");
    addDomainEvent(new BuyerVerifiedDomainEvent(this, orderId));
  }

  @NonNull
  @Override
  public BuyerSnapshot snapshot() {
    return BuyerSnapshot.builder()
        .id(id.getUuid())
        .userId(userId.getId())
        .buyerName(buyerName.getName())
        .build();
  }

}
