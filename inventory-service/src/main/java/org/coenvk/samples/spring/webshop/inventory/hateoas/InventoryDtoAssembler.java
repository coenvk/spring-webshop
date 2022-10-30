package org.coenvk.samples.spring.webshop.inventory.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.coenvk.samples.spring.webshop.common.dto.InventoryDto;
import org.coenvk.samples.spring.webshop.inventory.controller.query.InventoryQueryController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class InventoryDtoAssembler implements RepresentationModelAssembler<InventoryDto, EntityModel<InventoryDto>> {
    @Override
    public EntityModel<InventoryDto> toModel(InventoryDto inventory) {
        return EntityModel.of(inventory,
                linkTo(methodOn(InventoryQueryController.class).getInventoriesForWarehouse(
                        inventory.getWarehouseId())).withRel("inventories"),
                linkTo(methodOn(InventoryQueryController.class).getInventoriesForProduct(
                        inventory.getProductId())).withRel("inventories"));
    }
}