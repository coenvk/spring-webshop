package org.coenvk.samples.spring.webshop.shipment.mapper;

import java.util.Date;
import org.coenvk.samples.spring.webshop.common.dto.ShipmentDto;
import org.coenvk.samples.spring.webshop.common.dto.ShipmentItemDto;
import org.coenvk.samples.spring.webshop.common.dto.command.AddToShipment;
import org.coenvk.samples.spring.webshop.shipment.model.Shipment;
import org.coenvk.samples.spring.webshop.shipment.model.ShipmentItem;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DomainMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "shipment", ignore = true)
    ShipmentItem map(AddToShipment addToShipment);

    @Mapping(target = "shipmentId", source = "id.shipmentId")
    @Mapping(target = "orderId", source = "id.orderId")
    ShipmentItemDto map(ShipmentItem shipmentItem);

    @InheritConfiguration
    ShipmentItemDto update(ShipmentItem shipmentItem, @MappingTarget ShipmentItemDto shipmentItemDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "shipment", ignore = true)
    @InheritInverseConfiguration(name = "map")
    ShipmentItem map(ShipmentItemDto shipmentItemDto);

    @Mapping(target = "shipment", ignore = true)
    @InheritConfiguration
    ShipmentItem update(ShipmentItemDto shipmentItemDto, @MappingTarget ShipmentItem shipmentItem);

    @Mapping(target = "shipmentId", source = "id")
    ShipmentDto map(Shipment shipment);

    @InheritConfiguration
    ShipmentDto update(Shipment shipment, @MappingTarget ShipmentDto shipmentDto);

    @Mapping(target = "id", ignore = true)
    @InheritInverseConfiguration(name = "map")
    Shipment map(ShipmentDto shipmentDto);

    @InheritConfiguration
    Shipment update(ShipmentDto shipmentDto, @MappingTarget Shipment shipment);

    default Long toUnixTimestamp(Date date) {
        if (date == null) {
            return null;
        }
        return date.getTime() / 1000;
    }

    default Date fromUnixTimestamp(Long unix) {
        if (unix == null) {
            return null;
        }
        return new Date(unix * 1000);
    }
}
