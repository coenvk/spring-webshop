package org.coenvk.samples.spring.webshop.shipment.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.coenvk.samples.spring.webshop.common.dto.ShipmentDto;
import org.coenvk.samples.spring.webshop.shipment.controller.query.ShipmentQueryController;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class ShipmentDtoAssembler implements RepresentationModelAssembler<ShipmentDto, EntityModel<ShipmentDto>> {
    @Override
    public EntityModel<ShipmentDto> toModel(ShipmentDto shipment) {
        return EntityModel.of(shipment,
                linkTo(methodOn(ShipmentQueryController.class).getShipmentById(shipment.getShipmentId())).withSelfRel(),
                linkTo(methodOn(ShipmentQueryController.class).getShipments(Pageable.unpaged())).withRel("shipments"));
    }

    @Override
    public CollectionModel<EntityModel<ShipmentDto>> toCollectionModel(Iterable<? extends ShipmentDto> shipments) {
        return StreamSupport.stream(shipments.spliterator(), false).map(this::toModel).collect(
                Collectors.collectingAndThen(Collectors.toList(), ps -> CollectionModel.of(ps,
                        linkTo(methodOn(ShipmentQueryController.class).getShipments(
                                Pageable.unpaged())).withSelfRel())));
    }
}
