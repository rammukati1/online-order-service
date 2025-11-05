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

    /**
     * Create a new order.
     */
    @PostMapping
    public ResponseEntity<Order> createOrder(@Valid @RequestBody OrderProcessingDTO dto) {
        Order created = orderService.createOrder(dto);
        return ResponseEntity.created(URI.create("/api/orders/" + created.getId())).body(created);
    }

    /**
     * Get a single order by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        Order order = orderService.getOrder(id); // throws OrderNotFoundException if not found
        return ResponseEntity.ok(order);
    }

    /**
     * List all orders or filter by status.
     */
    @GetMapping
    public ResponseEntity<List<Order>> listOrders(@RequestParam Optional<OrderStatus> status) {
        List<Order> orders = orderService.listOrders(status);
        return ResponseEntity.ok(orders);
    }

    /**
     * Cancel an existing order.
     * Throws OrderNotFoundException or OrderAlreadyCancelledException if invalid.
     */
    @PostMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id); // may throw exception, handled globally
        return ResponseEntity.noContent().build();
    }

    /**
     * Update order status.
     */
    @PostMapping("/{id}/status")
    public ResponseEntity<Order> updateStatus(@PathVariable Long id, @RequestParam OrderStatus status) {
        Order updated = orderService.updateStatus(id, status); // may throw exception
        return ResponseEntity.ok(updated);
    }
}
