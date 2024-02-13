package net.greeta.stock.catalog.application.commandbus;

import lombok.RequiredArgsConstructor;
import net.greeta.stock.common.domain.dto.base.Command;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommandBusRetryHelper {

    private final CatalogCommandBus commandBus;

    @Retryable(retryFor = {Exception.class},
            maxAttempts = 5, backoff = @Backoff(delay = 1000))
    public <R, C extends Command<R>> R execute(C command) {
        return commandBus.execute(command);
    }


}
