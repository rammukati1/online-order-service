package com.online.order.repository;


import com.online.order.model.Order;
import com.online.order.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;


public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStatus(OrderStatus status);


    @Modifying
    @Query("update Order o set o.status = :newStatus where o.status = :oldStatus")
    int bulkUpdateStatus(@Param("oldStatus") OrderStatus oldStatus, @Param("newStatus") OrderStatus newStatus);
}