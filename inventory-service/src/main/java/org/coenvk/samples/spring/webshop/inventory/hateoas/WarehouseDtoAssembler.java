package org.coenvk.samples.spring.webshop.inventory.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.coenvk.samples.spring.webshop.common.dto.WarehouseDto;
import org.coenvk.samples.spring.webshop.inventory.controller.query.WarehouseQueryController;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class WarehouseDtoAssembler implements RepresentationModelAssembler<WarehouseDto, EntityModel<WarehouseDto>> {
    @Override
    public EntityModel<WarehouseDto> toModel(WarehouseDto warehouse) {
        return EntityModel.of(warehouse,
                linkTo(methodOn(WarehouseQueryController.class).getWarehouseById(warehouse.getWarehouseId())).withSelfRel(),
                linkTo(methodOn(WarehouseQueryController.class).getWarehouses(Pageable.unpaged())).withRel(
                        "warehouses"));
    }

    @Override
    public CollectionModel<EntityModel<WarehouseDto>> toCollectionModel(Iterable<? extends WarehouseDto> warehouses) {
        return StreamSupport.stream(warehouses.spliterator(), false).map(this::toModel).collect(
                Collectors.collectingAndThen(Collectors.toList(), ps -> CollectionModel.of(ps,
                        linkTo(methodOn(WarehouseQueryController.class).getWarehouses(
                                Pageable.unpaged())).withSelfRel())));
    }
}