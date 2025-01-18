package com.sparesparts.service;

import com.sparesparts.entity.OrderItem;
import com.sparesparts.entity.User;

import java.util.List;

/**
 * Service interface for OrderItem entity.
 */
public interface OrderItemService {

    /**
     * Create a new order item.
     * @param orderItem The order item to create.
     * @return The created order item.
     */
    OrderItem createOrderItem(OrderItem orderItem);

    /**
     * Get an order item by ID.
     * @param id The ID of the order item to retrieve.
     * @return The order item with the specified ID.
     */
    OrderItem getOrderItemById(Long id);

    /**
     * Update an existing order item.
     * @param orderItem The order item with updated details.
     * @return The updated order item.
     */
    OrderItem updateOrderItem(OrderItem orderItem);

    /**
     * Delete an order item by ID.
     * @param id The ID of the order item to delete.
     */
    void deleteOrderItem(Long id);


    List<OrderItem> getAllOrderItems();


    /**
     * Get all order items for a specific order of the authenticated customer.
     * @param orderId The ID of the order to retrieve items for.
     * @return List of order items for the specified order.
     */
    List<OrderItem> getOrderItemsByOrderId(Long orderId);


    List<Object[]> getTopPurchasedProductsByUser(User user);
}
