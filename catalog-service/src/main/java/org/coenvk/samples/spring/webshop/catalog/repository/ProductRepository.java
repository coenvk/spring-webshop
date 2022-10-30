package org.coenvk.samples.spring.webshop.catalog.repository;

import java.util.UUID;
import org.coenvk.samples.spring.webshop.catalog.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
}
