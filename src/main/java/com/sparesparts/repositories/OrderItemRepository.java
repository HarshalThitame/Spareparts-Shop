package com.sparesparts.repositories;

import com.sparesparts.entity.OrderItem;
import com.sparesparts.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query("SELECT oi.product, SUM(oi.quantity) as totalQuantity " +
            "FROM OrderItem oi " +
            "GROUP BY oi.product " +
            "ORDER BY totalQuantity DESC")
    List<Object[]> findTopSellingProducts();

    @Query("SELECT oi.product.id, oi.product.name, COUNT(oi.product.id) AS purchaseCount " +
            "FROM OrderItem oi " +
            "JOIN oi.order o " +
            "WHERE o.user = :user AND o.status = 'PAID' " +
            "GROUP BY oi.product.id, oi.product.name " +
            "ORDER BY purchaseCount DESC")
    List<Object[]> findTopPurchasedProductsByUser(@Param("user") User user);

}
