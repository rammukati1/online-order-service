package com.online.order.controller;

import com.online.order.model.Order;
import com.online.order.model.OrderProcessingDTO;
import com.online.order.model.OrderStatus;
import com.online.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.net.URI;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;


    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping
    public ResponseEntity<Order> createOrder(@Valid @RequestBody OrderProcessingDTO dto) {
        Order created = orderService.createOrder(dto);
        return ResponseEntity.created(URI.create("/api/orders/" + created.getId())).body(created);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        return orderService.getOrder(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping
    public ResponseEntity<List<Order>> listOrders(@RequestParam Optional<OrderStatus> status) {
        return ResponseEntity.ok(orderService.listOrders(status));
    }


    @PostMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long id) {
        boolean ok = orderService.cancelOrder(id);
        return ok ? ResponseEntity.noContent().build() : ResponseEntity.status(409).build();
    }


    @PostMapping("/{id}/status")
    public ResponseEntity<Order> updateStatus(@PathVariable Long id, @RequestParam OrderStatus status) {
        Optional<Order> updated = orderService.updateStatus(id, status);
        return updated.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
