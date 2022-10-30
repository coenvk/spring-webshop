package org.coenvk.samples.spring.webshop.order.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.coenvk.samples.spring.toolkit.ToolkitCollection;
import org.coenvk.samples.spring.toolkit.ToolkitReflection;
import org.coenvk.samples.spring.webshop.order.mapper.DomainMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableJpaRepositories(value = "org.coenvk.samples.spring.webshop.order")
public class ApplicationConfiguration extends WebMvcAutoConfiguration {
    @Autowired
    public void configMapper(ObjectMapper mapper) {
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.enable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
    }

    @Bean
    @Profile({"dev", "test", "acceptance"})
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("*")
                        .allowedHeaders("*")
                        .allowedOrigins("*")
                        .allowCredentials(true);
            }
        };
    }

    @Bean
    public ToolkitCollection toolkitCollection() {
        return new ToolkitCollection();
    }

    @Bean
    @Autowired
    public ToolkitReflection toolkitReflection(ToolkitCollection toolkitCollection) {
        return new ToolkitReflection(toolkitCollection);
    }

    @Bean
    public DomainMapper mapper() {
        return Mappers.getMapper(DomainMapper.class);
    }
}