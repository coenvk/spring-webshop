package org.coenvk.samples.spring.webshop.payment.config.scs;

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
    @Bean(name = "paymentSink")
    public Sinks.Many<?> paymentSink() {
        return Sinks.many().multicast().onBackpressureBuffer();
    }

    @Bean
    public Supplier<Flux<Message<?>>> paymentSupplier(@Qualifier(value = "paymentSink") Sinks.Many<?> sink) {
        return () -> sink.asFlux().map(payload -> MessageBuilder
                .withPayload(payload)
                .setHeader("type", payload.getClass().getName())
                .build());
    }
}
