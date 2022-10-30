package org.coenvk.samples.spring.webshop.order.config.scs;

import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.cloud.stream.config.BinderFactoryAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Configuration
@AutoConfigureAfter(BinderFactoryAutoConfiguration.class)
public class SupplierConfiguration {
    @Bean(name = "orderSink")
    public Sinks.Many<?> orderSink() {
        return Sinks.many().multicast().onBackpressureBuffer();
    }

    @Bean
    public Supplier<Flux<Message<?>>> orderSupplier(@Qualifier(value = "orderSink") Sinks.Many<?> sink) {
        return () -> sink.asFlux().map(payload -> MessageBuilder
                .withPayload(payload)
                .setHeader("type", payload.getClass().getName())
                .build());
    }

    @Bean(name = "sagaSink")
    public Sinks.Many<?> sagaSink() {
        return Sinks.many().multicast().onBackpressureBuffer();
    }

    @Bean
    public Supplier<Flux<Message<?>>> sagaSupplier(@Qualifier(value = "sagaSink") Sinks.Many<?> sink) {
        return () -> sink.asFlux().map(payload -> MessageBuilder
                .withPayload(payload)
                .setHeader("type", payload.getClass().getName())
                .build());
    }
}
