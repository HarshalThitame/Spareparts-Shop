package com.sparesparts.controller.customer;

import com.sparesparts.entity.OrderItem;
import com.sparesparts.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing order items of the authenticated customer.
 */
@RestController
@RequestMapping("/api/customer/order-items")
public class CustomerOrderItemController {

    private final OrderItemService orderItemService;

    @Autowired
    public CustomerOrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    /**
     * Get all order items for a specific order of the authenticated customer.
     * @param orderId The ID of the order to retrieve items for.
     * @return List of order items for the specified order.
     */
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderItem>> getOrderItemsByOrderId(@PathVariable Long orderId) {
        List<OrderItem> orderItems = orderItemService.getOrderItemsByOrderId(orderId); // Implement method to retrieve items
        return ResponseEntity.ok(orderItems);
    }

    /**
     * Get an order item by its ID for the authenticated customer.
     * @param id The ID of the order item to retrieve.
     * @return The order item with the specified ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderItem> getOrderItemById(@PathVariable Long id) {
        OrderItem orderItem = orderItemService.getOrderItemById(id); // Implement method to retrieve a specific order item
        return ResponseEntity.ok(orderItem);
    }

    // Additional methods for customer to update or delete their order items can be added here.
}
