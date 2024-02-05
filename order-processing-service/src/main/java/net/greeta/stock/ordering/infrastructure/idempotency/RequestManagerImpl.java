package net.greeta.stock.ordering.infrastructure.idempotency;

import lombok.RequiredArgsConstructor;
import net.greeta.stock.common.domain.dto.order.exceptions.OrderingDomainException;
import net.greeta.stock.ordering.api.infrastructure.requestmanager.RequestManager;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RequestManagerImpl implements RequestManager {

  private final ClientRequestRepository clientRequestRepository;

  @Override
  public boolean exist(UUID id) {
    return clientRequestRepository.findById(id).isPresent();
  }

  @Override
  public void createRequestForCommand(UUID id, String commandName) {
    if (exist(id)) {
      throw new OrderingDomainException("Request with id: %s already exists".formatted(id));
    }

    clientRequestRepository.save(new ClientRequest(id, commandName, LocalDateTime.now()));
  }
}
