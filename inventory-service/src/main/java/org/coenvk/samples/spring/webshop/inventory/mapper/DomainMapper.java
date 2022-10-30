package org.coenvk.samples.spring.webshop.inventory.mapper;

import org.coenvk.samples.spring.webshop.common.dto.InventoryDto;
import org.coenvk.samples.spring.webshop.common.dto.WarehouseDto;
import org.coenvk.samples.spring.webshop.inventory.model.Inventory;
import org.coenvk.samples.spring.webshop.inventory.model.Warehouse;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DomainMapper {
    @Mapping(target = "productId", source = "id.productId")
    @Mapping(target = "warehouseId", source = "id.warehouseId")
    InventoryDto map(Inventory inventory);

    @InheritConfiguration
    InventoryDto update(Inventory inventory, @MappingTarget InventoryDto inventoryDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "warehouse", ignore = true)
    @InheritInverseConfiguration(name = "map")
    Inventory map(InventoryDto inventoryDto);

    @Mapping(target = "warehouse", ignore = true)
    @InheritConfiguration
    Inventory update(InventoryDto inventoryDto, @MappingTarget Inventory inventory);

    @Mapping(target = "warehouseId", source = "id")
    WarehouseDto map(Warehouse warehouse);

    @InheritConfiguration
    WarehouseDto update(Warehouse warehouse, @MappingTarget WarehouseDto warehouseDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "inventories", ignore = true)
    @InheritInverseConfiguration(name = "map")
    Warehouse map(WarehouseDto warehouseDto);

    @InheritConfiguration
    Warehouse update(WarehouseDto warehouseDto, @MappingTarget Warehouse warehouse);
}
