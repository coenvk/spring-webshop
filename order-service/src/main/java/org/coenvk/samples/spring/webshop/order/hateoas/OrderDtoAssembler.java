package org.coenvk.samples.spring.webshop.order.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.coenvk.samples.spring.webshop.common.dto.OrderDto;
import org.coenvk.samples.spring.webshop.order.controller.query.OrderQueryController;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class OrderDtoAssembler implements RepresentationModelAssembler<OrderDto, EntityModel<OrderDto>> {
    @Override
    public EntityModel<OrderDto> toModel(OrderDto order) {
        return EntityModel.of(order,
                linkTo(methodOn(OrderQueryController.class).getOrderById(order.getOrderId())).withSelfRel(),
                linkTo(methodOn(OrderQueryController.class).getOrders(Pageable.unpaged())).withRel("orders"));
    }

    @Override
    public CollectionModel<EntityModel<OrderDto>> toCollectionModel(Iterable<? extends OrderDto> orders) {
        return StreamSupport.stream(orders.spliterator(), false).map(this::toModel).collect(
                Collectors.collectingAndThen(Collectors.toList(), ps -> CollectionModel.of(ps,
                        linkTo(methodOn(OrderQueryController.class).getOrders(
                                Pageable.unpaged())).withSelfRel())));
    }
}
