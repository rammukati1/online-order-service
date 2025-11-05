package com.online.order.service;

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


    @Transactional
    public Order createOrder(OrderProcessingDTO dto) {
        Order order = new Order();
        order.setCustomerName(dto.getCustomerName());
        order.setStatus(OrderStatus.PENDING);
        if (dto.getItems() != null) {
            order.setItems(dto.getItems().stream().map(i -> {
                OrderItem it = new OrderItem();
                it.setProductName(i.productName);
                it.setQuantity(i.quantity);
                it.setUnitPrice(i.unitPrice);
                return it;
            }).collect(Collectors.toList()));
        }
        return orderRepository.save(order);
    }


    public Optional<Order> getOrder(Long id) {
        return orderRepository.findById(id);
    }


    public List<Order> listOrders(Optional<OrderStatus> status) {
        return status.map(orderRepository::findByStatus).orElseGet(orderRepository::findAll);
    }


    @Transactional
    public boolean cancelOrder(Long id) {
        Optional<Order> opt = orderRepository.findById(id);
        if (opt.isEmpty()) return false;
        Order order = opt.get();
        if (order.getStatus() != OrderStatus.PENDING) return false;
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
        return true;
    }


    @Transactional
    public Optional<Order> updateStatus(Long id, OrderStatus newStatus) {
        Optional<Order> opt = orderRepository.findById(id);
        if (opt.isEmpty()) return Optional.empty();
        Order order = opt.get();
        order.setStatus(newStatus);
        return Optional.of(orderRepository.save(order));
    }



    @Transactional
    public int bulkPromotePendingToProcessing() {
        return orderRepository.bulkUpdateStatus(OrderStatus.PENDING, OrderStatus.PROCESSING);
    }
}