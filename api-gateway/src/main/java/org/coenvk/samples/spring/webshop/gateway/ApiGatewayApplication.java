package org.coenvk.samples.spring.webshop.gateway;

import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.bulkhead.ThreadPoolBulkheadConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import java.time.Duration;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4jBulkheadProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class ApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public Customizer<Resilience4jBulkheadProvider> bulkheadCustomizer() {
        return provider -> provider.configure(builder -> builder
                .bulkheadConfig(BulkheadConfig.custom().maxConcurrentCalls(4).build())
                .threadPoolBulkheadConfig(ThreadPoolBulkheadConfig.ofDefaults()));
    }

    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> circuitBreakerCustomizer() {
        return factory -> factory.configure(builder -> builder
                .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
                .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(2)).build()));
    }
}

@RestController
@RequestMapping("services")
class DiscoveryController {
    private final DiscoveryClient discoveryClient;

    @Autowired
    public DiscoveryController(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @GetMapping("{applicationName}")
    public ResponseEntity<List<ServiceInstance>> serviceInstances(@PathVariable String applicationName) {
        return ResponseEntity.ok(discoveryClient.getInstances(applicationName));
    }

    @GetMapping("names")
    public ResponseEntity<List<String>> serviceNames() {
        return ResponseEntity.ok(discoveryClient.getServices());
    }

    @GetMapping
    public ResponseEntity<List<ServiceInstance>> serviceInstances() {
        return ResponseEntity.ok(discoveryClient.getServices().stream().flatMap(
                serviceName -> discoveryClient.getInstances(serviceName).stream()).toList());
    }
}