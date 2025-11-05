package com.online.order.service;

import com.online.order.exception.InvalidOrderStatusException;
import com.online.order.exception.OrderAlreadyCancelledException;
import com.online.order.exception.OrderNotFoundException;
import com.online.order.model.Order;
import com.online.order.model.OrderItem;
import com.online.order.model.OrderProcessingDTO;
import com.online.order.model.OrderStatus;
import com.online.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Create a new order with items and default PENDING status.
     */
    @Transactional
    public Order createOrder(OrderProcessingDTO dto) {
        Order order = new Order();
        order.setCustomerName(dto.getCustomerName());
        order.setStatus(OrderStatus.PENDING);

        if (dto.getItems() != null && !dto.getItems().isEmpty()) {
            order.setItems(dto.getItems().stream().map(i -> {
                OrderItem item = new OrderItem();
                item.setProductName(i.productName);
                item.setQuantity(i.quantity);
                item.setUnitPrice(i.unitPrice);
                return item;
            }).collect(Collectors.toList()));
        }

        return orderRepository.save(order);
    }

    /**
     * Fetch an order by ID, or throw exception if not found.
     */
    public Order getOrder(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    /**
     * Fetch all orders or filter by status if provided.
     */
    public List<Order> listOrders(Optional<OrderStatus> status) {
        return status.map(orderRepository::findByStatus)
                .orElseGet(orderRepository::findAll);
    }

    /**
     * Cancel a pending order.
     * Throws exception if order not found or cannot be cancelled.
     */
    @Transactional
    public void cancelOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new OrderAlreadyCancelledException(id);
        }

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    /**
     * Update an order's status.
     */
    @Transactional
    public Order updateStatus(Long id, OrderStatus newStatus) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        if (newStatus == null) {
            throw new InvalidOrderStatusException("Status cannot be null");
        }

        // Simple example of a valid transition rule
        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new InvalidOrderStatusException("Cannot update a cancelled order");
        }

        order.setStatus(newStatus);
        return orderRepository.save(order);
    }

    /**
     * Promote all PENDING orders to PROCESSING.
     */
    @Transactional
    public int bulkPromotePendingToProcessing() {
        return orderRepository.bulkUpdateStatus(OrderStatus.PENDING, OrderStatus.PROCESSING);
    }
}
