package org.coenvk.samples.spring.webshop.catalog.controller.command;

import java.util.UUID;
import javax.validation.Valid;
import org.coenvk.samples.spring.webshop.catalog.service.ProductService;
import org.coenvk.samples.spring.webshop.common.dto.ProductDto;
import org.coenvk.samples.spring.webshop.common.dto.command.CreateProduct;
import org.coenvk.samples.spring.webshop.common.dto.command.DeleteProduct;
import org.coenvk.samples.spring.webshop.common.dto.command.UpdateProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProductCommandController {
    private final ProductService _service;

    @Autowired
    public ProductCommandController(
            ProductService service
    ) {
        _service = service;
    }

    @PostMapping("product")
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody CreateProduct command) {
        return ResponseEntity.ok(_service.createProduct(command));
    }

    @DeleteMapping("product/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID productId) {
        _service.deleteProduct(DeleteProduct.of(productId));
        return ResponseEntity.noContent().build();
    }

    @PutMapping("product")
    public ResponseEntity<ProductDto> updateProduct(@Valid @RequestBody UpdateProduct command) {
        return ResponseEntity.ok(_service.updateProduct(command));
    }
}
