package net.greeta.stock.shared.rest.autoconfiguration;

import net.greeta.stock.shared.rest.config.GlobalConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(GlobalConfiguration.class)
public class EshopRestAutoconfiguration {
}
