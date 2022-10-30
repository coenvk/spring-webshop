package org.coenvk.samples.spring.webshop.catalog.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.coenvk.samples.spring.webshop.catalog.controller.query.ProductQueryController;
import org.coenvk.samples.spring.webshop.common.dto.ProductDto;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class ProductDtoAssembler implements RepresentationModelAssembler<ProductDto, EntityModel<ProductDto>> {
    @Override
    public EntityModel<ProductDto> toModel(ProductDto product) {
        return EntityModel.of(product,
                linkTo(methodOn(ProductQueryController.class).getProductById(product.getProductId())).withSelfRel(),
                linkTo(methodOn(ProductQueryController.class).getProducts(Pageable.unpaged())).withRel("products"));
    }

    @Override
    public CollectionModel<EntityModel<ProductDto>> toCollectionModel(Iterable<? extends ProductDto> products) {
        return StreamSupport.stream(products.spliterator(), false).map(this::toModel).collect(
                Collectors.collectingAndThen(Collectors.toList(), ps -> CollectionModel.of(ps,
                        linkTo(methodOn(ProductQueryController.class).getProducts(Pageable.unpaged())).withSelfRel())));
    }
}
