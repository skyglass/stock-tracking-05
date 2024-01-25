package net.greeta.stock.ordering.infrastructure.idempotency;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientRequestRepository extends JpaRepository<ClientRequest, UUID> {
}
