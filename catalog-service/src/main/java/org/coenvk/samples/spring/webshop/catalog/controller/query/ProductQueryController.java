package org.coenvk.samples.spring.webshop.catalog.controller.query;

import java.util.UUID;
import org.coenvk.samples.spring.toolkit.error.exceptions.ModelNotFound;
import org.coenvk.samples.spring.webshop.catalog.hateoas.ProductDtoAssembler;
import org.coenvk.samples.spring.webshop.catalog.mapper.DomainMapper;
import org.coenvk.samples.spring.webshop.catalog.repository.ProductRepository;
import org.coenvk.samples.spring.webshop.common.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProductQueryController {
    private final ProductRepository _repository;
    private final DomainMapper _mapper;
    private final ProductDtoAssembler _assembler;

    @Autowired
    public ProductQueryController(
            ProductRepository repository,
            DomainMapper mapper,
            ProductDtoAssembler assembler) {
        _repository = repository;
        _mapper = mapper;
        _assembler = assembler;
    }

    @GetMapping("product")
    public ResponseEntity<CollectionModel<EntityModel<ProductDto>>> getProducts(Pageable pageable) {
        var products = _repository.findAll(pageable)
                .stream()
                .map(_mapper::map)
                .toList();
        return ResponseEntity.ok(_assembler.toCollectionModel(products));
    }

    @GetMapping("product/{productId}")
    public ResponseEntity<EntityModel<ProductDto>> getProductById(@PathVariable UUID productId) {
        var product = _repository.findById(productId)
                .orElseThrow(ModelNotFound::new);

        var productDto = _mapper.map(product);
        return ResponseEntity.ok(_assembler.toModel(productDto));
    }
}
