package org.coenvk.samples.spring.webshop.order.controller.query;

import java.util.UUID;
import org.coenvk.samples.spring.toolkit.error.exceptions.ModelNotFound;
import org.coenvk.samples.spring.webshop.common.dto.OrderDto;
import org.coenvk.samples.spring.webshop.order.hateoas.OrderDtoAssembler;
import org.coenvk.samples.spring.webshop.order.mapper.DomainMapper;
import org.coenvk.samples.spring.webshop.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OrderQueryController {
    private final OrderRepository _orderRepository;
    private final DomainMapper _mapper;
    private final OrderDtoAssembler _assembler;

    @Autowired
    public OrderQueryController(OrderRepository orderRepository,
                                DomainMapper mapper,
                                OrderDtoAssembler assembler) {
        _orderRepository = orderRepository;
        _mapper = mapper;
        _assembler = assembler;
    }

    @GetMapping("order")
    @ResponseBody
    public ResponseEntity<CollectionModel<EntityModel<OrderDto>>> getOrders(Pageable pageable) {
        var orders = _orderRepository.findAll(pageable)
                .stream()
                .map(_mapper::map)
                .toList();
        return ResponseEntity.ok(_assembler.toCollectionModel(orders));
    }

    @GetMapping("order/{orderId}")
    @ResponseBody
    public ResponseEntity<EntityModel<OrderDto>> getOrderById(@PathVariable UUID orderId) {
        var order = _orderRepository.findById(orderId).orElseThrow(ModelNotFound::new);
        var orderDto = _mapper.map(order);
        return ResponseEntity.ok(_assembler.toModel(orderDto));
    }
}
