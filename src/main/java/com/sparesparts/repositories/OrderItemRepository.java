package com.sparesparts.repositories;

import com.sparesparts.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for OrderItem entity.
 */
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    /**
     * Find all order items by order ID.
     * @param orderId The ID of the order.
     * @return List of order items associated with the specified order.
     */
    List<OrderItem> findByOrderId(Long orderId);
}
