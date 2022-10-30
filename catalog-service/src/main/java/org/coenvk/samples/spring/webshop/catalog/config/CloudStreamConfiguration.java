package org.coenvk.samples.spring.webshop.catalog.config;

import java.util.function.Supplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Configuration
public class CloudStreamConfiguration {
    @Bean
    public Sinks.Many<?> catalogSink() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    @Bean
    public Supplier<Flux<Message<?>>> catalogSupplier(Sinks.Many<?> sink) {
        return () -> sink.asFlux().map(payload -> MessageBuilder
                .withPayload(payload)
                .setHeader("type", payload.getClass().getName())
                .build());
    }
}
