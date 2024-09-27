package com.sparesparts.controller.customer;


import com.sparesparts.entity.Order;
import com.sparesparts.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing customer orders.
 */
@RestController
@RequestMapping("/api/customer/orders")
public class CustomerOrderController {

    private final OrderService orderService;

    @Autowired
    public CustomerOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Get all orders for a specific user ID.
     * @param userId The ID of the user whose orders to retrieve.
     * @return List of orders for the specified user.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable Long userId) {
        List<Order> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }

    /**
     * Get all orders for the authenticated customer.
     * @return List of orders for the customer.
     */
    @GetMapping
    public ResponseEntity<List<Order>> getAllCustomerOrders() {
        List<Order> orders = orderService.getAllCustomerOrders(); // Implement method to get orders for the customer
        return ResponseEntity.ok(orders);
    }

    /**
     * Get an order by its ID for the authenticated customer.
     * @param id The ID of the order to retrieve.
     * @return The order with the specified ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Order> getCustomerOrderById(@PathVariable Long id) {
        Order order = orderService.getCustomerOrderById(id); // Implement method to get a specific order
        return ResponseEntity.ok(order);
    }

    /**
     * Place a new order for the authenticated customer.
     * @param order The order details to be placed.
     * @return The placed order.
     */
    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestBody Order order) {
        Order placedOrder = orderService.createOrder(order); // Implement method to place a new order
        return ResponseEntity.ok(placedOrder);
    }

    /**
     * Cancel an existing order for the authenticated customer.
     * @param id The ID of the order to cancel.
     * @return Response indicating the cancellation result.
     */
    @DeleteMapping("/{id}/cancel")
    public ResponseEntity<String> cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id); // Implement method to cancel an order
        return ResponseEntity.ok("Order canceled successfully");
    }
}
