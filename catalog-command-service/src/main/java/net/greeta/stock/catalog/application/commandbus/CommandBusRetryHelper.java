package net.greeta.stock.catalog.application.commandbus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.greeta.stock.common.domain.dto.base.Command;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
@RequiredArgsConstructor
@Slf4j
public class CommandBusRetryHelper {

    private final CatalogCommandBus commandBus;

    private final AtomicInteger counter = new AtomicInteger(0);

    @Retryable(retryFor = {Exception.class},
            maxAttempts = 5, backoff = @Backoff(delay = 1000))
    public <R, C extends Command<R>> R execute(C command) {
        log.info("CommandBusRetryHelper.execute started retry {} for command {}", counter.getAndIncrement(), command.getClass().getSimpleName());
        return commandBus.execute(command);
    }


}
