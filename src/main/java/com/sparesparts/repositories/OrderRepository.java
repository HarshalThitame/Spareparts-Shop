package com.sparesparts.repositories;

import com.sparesparts.entity.Order;
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
     * @param userId The ID of the user.
     * @return List of orders associated with the user.
     */
    List<Order> findByUserId(Long userId);

    /**
     * Find orders by their status.
     * @param status The status of the orders to find.
     * @return List of orders with the specified status.
     */
    List<Order> findByStatus(String status);

    @Query("SELECT o FROM Order o WHERE o.updatedAt >= :timestamp")
    List<Order> findUpdatedRecently(@Param("timestamp") LocalDateTime timestamp);}
