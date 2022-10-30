package org.coenvk.samples.spring.webshop.catalog.service;

import org.coenvk.samples.spring.toolkit.error.exceptions.ModelNotFound;
import org.coenvk.samples.spring.webshop.catalog.mapper.DomainMapper;
import org.coenvk.samples.spring.webshop.catalog.repository.ProductRepository;
import org.coenvk.samples.spring.webshop.common.dto.ProductDto;
import org.coenvk.samples.spring.webshop.common.dto.command.CreateProduct;
import org.coenvk.samples.spring.webshop.common.dto.command.DeleteProduct;
import org.coenvk.samples.spring.webshop.common.dto.command.UpdateProduct;
import org.coenvk.samples.spring.webshop.common.dto.event.ProductCreated;
import org.coenvk.samples.spring.webshop.common.dto.event.ProductDeleted;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

@Service
public class ProductService {
    private final ProductRepository _repository;
    private final DomainMapper _mapper;
    private final Sinks.Many<Object> _catalogSink;

    @Autowired
    public ProductService(
            ProductRepository repository,
            DomainMapper mapper,
            Sinks.Many<Object> catalogSink) {
        _repository = repository;
        _mapper = mapper;
        _catalogSink = catalogSink;
    }

    public ProductDto createProduct(CreateProduct command) {
        var product = _mapper.map(command);
        var savedProduct = _repository.save(product);
        _catalogSink.tryEmitNext(new ProductCreated(savedProduct.getId()));
        return _mapper.map(savedProduct);
    }

    public ProductDto updateProduct(UpdateProduct command) {
        var product = _repository.findById(command.getProductId())
                .orElseThrow(ModelNotFound::new);

        _mapper.update(command, product);

        var savedProduct = _repository.save(product);
        return _mapper.map(savedProduct);
    }

    public void deleteProduct(DeleteProduct command) {
        var productId = command.getProductId();
        _repository.deleteById(productId);
        _catalogSink.tryEmitNext(new ProductDeleted(productId));
    }
}
