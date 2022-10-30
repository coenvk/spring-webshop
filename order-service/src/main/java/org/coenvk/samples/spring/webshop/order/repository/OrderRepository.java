package org.coenvk.samples.spring.webshop.order.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.coenvk.samples.spring.webshop.order.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    @Override
    @EntityGraph("order:orderLines")
    Optional<Order> findById(UUID uuid);

    @Override
    @EntityGraph("order:orderLines")
    List<Order> findAll();

    @Override
    @EntityGraph("order:orderLines")
    Page<Order> findAll(Pageable pageable);
}
