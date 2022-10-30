package org.coenvk.samples.spring.webshop.catalog.mapper;

import org.coenvk.samples.spring.webshop.catalog.model.Product;
import org.coenvk.samples.spring.webshop.common.dto.ProductDto;
import org.coenvk.samples.spring.webshop.common.dto.command.CreateProduct;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DomainMapper {
    Product map(CreateProduct createProduct);

    @InheritConfiguration
    Product update(CreateProduct createProduct, @MappingTarget Product product);

    @Mapping(target = "productId", source = "id")
    ProductDto map(Product product);

    @InheritConfiguration
    ProductDto update(Product product, @MappingTarget ProductDto productDto);

    @InheritInverseConfiguration(name = "map")
    Product map(ProductDto productDto);

    @InheritInverseConfiguration(name = "update")
    Product update(ProductDto productDto, @MappingTarget Product product);
}
