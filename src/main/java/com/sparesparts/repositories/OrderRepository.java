package com.sparesparts.repositories;

import com.sparesparts.entity.Enum.OrderStatus;
import com.sparesparts.entity.Order;
import com.sparesparts.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for Order entity.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Find all orders by user ID.
     *
     * @param userId The ID of the user.
     * @return List of orders associated with the user.
     */
    List<Order> findByUserId(Long userId);

    /**
     * Find orders by their status.
     *
     * @param status The status of the orders to find.
     * @return List of orders with the specified status.
     */
    List<Order> findByStatus(String status);

    @Query("SELECT o FROM Order o WHERE o.updatedAt >= :timestamp")
    List<Order> findUpdatedRecently(@Param("timestamp") LocalDateTime timestamp);

    List<Order> findByIsViewedFalse(Sort sort);
    long countByIsViewedFalse();

    // This method will return paginated data
    Page<Order> findAll(Pageable pageable);

    // Example with filtering by status
    Page<Order> findByStatus(OrderStatus status, Pageable pageable);

    Page<Order> findByIsVorTrue(Pageable pageable);

    // Query to get total orders count and total spent by a specific user
    @Query("SELECT COUNT(o) AS totalCount, SUM(o.totalAmount) AS totalSpent " +
            "FROM Order o " +
            "WHERE o.user = :user AND o.status = 'PAID'")
    Object[] findTotalOrdersCountAndSpentByUser(@Param("user") User user);

}


