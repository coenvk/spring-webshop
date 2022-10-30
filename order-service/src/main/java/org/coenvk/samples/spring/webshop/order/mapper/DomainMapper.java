package org.coenvk.samples.spring.webshop.order.mapper;

import org.coenvk.samples.spring.webshop.common.dto.OrderDto;
import org.coenvk.samples.spring.webshop.common.dto.OrderLineDto;
import org.coenvk.samples.spring.webshop.common.dto.command.CreateOrder;
import org.coenvk.samples.spring.webshop.order.model.Order;
import org.coenvk.samples.spring.webshop.order.model.OrderLine;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DomainMapper {
    Order map(CreateOrder createOrder);

    @Mapping(target = "orderId", source = "id")
    OrderDto map(Order order);

    @InheritConfiguration
    OrderDto update(Order order, @MappingTarget OrderDto orderDto);

    @Mapping(target = "id", ignore = true)
    @InheritInverseConfiguration(name = "map")
    Order map(OrderDto orderDto);

    @InheritConfiguration
    Order update(OrderDto orderDto, @MappingTarget Order order);

    OrderLineDto map(OrderLine orderLine);

    @InheritConfiguration
    OrderLineDto update(OrderLine orderLine, @MappingTarget OrderLineDto orderLineDto);

    @Mapping(target = "order", ignore = true)
    @InheritInverseConfiguration(name = "map")
    OrderLine map(OrderLineDto orderLineDto);

    @InheritConfiguration
    OrderLine update(OrderLineDto orderLineDto, @MappingTarget OrderLine orderLine);
}
