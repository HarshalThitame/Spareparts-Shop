package com.sparesparts.service;


import com.sparesparts.entity.Order;
import java.util.List;

/**
 * Service interface for Order entity.
 */
public interface OrderService {

    /**
     * Create a new order.
     * @param order The order to create.
     * @return The created order.
     */
    Order createOrder(Order order);

    /**
     * Get an order by ID.
     * @param id The ID of the order to retrieve.
     * @return The order with the specified ID.
     */
    Order getOrderById(Long id);

    /**
     * Update an existing order.
     * @param order The order with updated details.
     * @return The updated order.
     */
    Order updateOrder(Order order);

    /**
     * Delete an order by ID.
     * @param id The ID of the order to delete.
     */
    void deleteOrder(Long id);

    /**
     * Get all orders for a specific user.
     * @param userId The ID of the user whose orders to retrieve.
     * @return List of orders associated with the user.
     */
    List<Order> getOrdersByUserId(Long userId);

    /**
     * Get all orders with a specific status.
     * @param status The status of the orders to retrieve.
     * @return List of orders with the specified status.
     */
    List<Order> getOrdersByStatus(String status);

    List<Order> getAllOrders();

    /**
     * Get all orders for the authenticated customer.
     * @return List of orders.
     */
    List<Order> getAllCustomerOrders();

    /**
     * Get an order by its ID for the authenticated customer.
     * @param id The ID of the order to retrieve.
     * @return The order with the specified ID.
     */
    Order getCustomerOrderById(Long id);

    /**
     * Place a new order for the authenticated customer.
     * @param order The order details to be placed.
     * @return The placed order.
     */
    Order placeOrder(Order order);

    /**
     * Cancel an existing order for the authenticated customer.
     * @param id The ID of the order to cancel.
     */
    void cancelOrder(Long id);

    Order markedAsViewed(Long id);

    Order markedAsUnViewed(Long id);

    List<Order> getOrderUpdates(); // Method to retrieve order updates

    public List<Order> getUnseenOrders();

    public long getUnseenOrderCount();

}
