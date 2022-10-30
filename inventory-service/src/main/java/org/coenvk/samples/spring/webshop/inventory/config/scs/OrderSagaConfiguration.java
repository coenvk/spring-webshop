package org.coenvk.samples.spring.webshop.inventory.config.scs;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import org.coenvk.samples.spring.webshop.inventory.service.OrderSagaHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.cloud.stream.config.BinderFactoryAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.CompositeMessageConverter;
import org.springframework.messaging.support.MessageBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
@AutoConfigureAfter(BinderFactoryAutoConfiguration.class)
public class OrderSagaConfiguration {
    private final OrderSagaHandler _orderSagaHandler;

    @Autowired
    public OrderSagaConfiguration(OrderSagaHandler orderSagaHandler) {
        _orderSagaHandler = orderSagaHandler;
    }

    private Class<?> resolveTargetClass(Message<?> message) {
        return Optional.of(message.getHeaders())
                .filter(headers -> headers.containsKey("type"))
                .map(headers -> headers.get("type"))
                .map(type -> {
                    try {
                        return Class.forName(String.valueOf(type));
                    } catch (ClassNotFoundException e) {
                        return Object.class;
                    }
                })
                .orElse(Object.class);
    }

    @Bean
    public Function<Flux<Message<?>>, Flux<Message<Object>>> sagaProcessor(CompositeMessageConverter compositeMessageConverter) {
        return flux -> flux.flatMap(message -> {
            var targetClass = resolveTargetClass(message);
            var event = compositeMessageConverter.fromMessage(message, targetClass);
            return Mono.fromSupplier(() -> {
                var payload = _orderSagaHandler.on(event);
                if (payload == null) return null;
                return MessageBuilder
                        .withPayload(payload)
                        .setHeader("type", payload.getClass().getName())
                        .build();
            });
        }).filter(Objects::nonNull);
    }
}
