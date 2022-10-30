package org.coenvk.samples.spring.webshop.inventory.config.scs;

import java.util.Optional;
import java.util.function.Consumer;
import org.coenvk.samples.spring.webshop.inventory.service.CatalogEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.cloud.stream.config.BinderFactoryAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.CompositeMessageConverter;

@Configuration
@AutoConfigureAfter(BinderFactoryAutoConfiguration.class)
public class ConsumerConfiguration {
    private final CatalogEventHandler _catalogEventHandler;

    @Autowired
    public ConsumerConfiguration(
            CatalogEventHandler catalogEventHandler) {
        _catalogEventHandler = catalogEventHandler;
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
    public Consumer<Message<?>> catalogConsumer(CompositeMessageConverter compositeMessageConverter) {
        return message -> {
            var targetClass = resolveTargetClass(message);
            var event = compositeMessageConverter.fromMessage(message, targetClass);
            _catalogEventHandler.on(event);
        };
    }
}
