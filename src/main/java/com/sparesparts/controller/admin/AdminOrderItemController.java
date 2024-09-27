package com.sparesparts.controller.admin;

import com.sparesparts.entity.OrderItem;
import com.sparesparts.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing order items by the admin.
 */
@RestController
@RequestMapping("/api/admin/order-items")
public class AdminOrderItemController {

    private final OrderItemService orderItemService;

    @Autowired
    public AdminOrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    /**
     * Get a list of all order items.
     * @return List of all order items.
     */
    @GetMapping
    public ResponseEntity<List<OrderItem>> getAllOrderItems() {
        List<OrderItem> orderItems = orderItemService.getAllOrderItems();
        return ResponseEntity.ok(orderItems);
    }

    /**
     * Get an order item by its ID.
     * @param id The ID of the order item to retrieve.
     * @return The order item with the specified ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderItem> getOrderItemById(@PathVariable Long id) {
        OrderItem orderItem = orderItemService.getOrderItemById(id);
        return ResponseEntity.ok(orderItem);
    }

    /**
     * Update an existing order item.
     * @param id The ID of the order item to update.
     * @param orderItem The updated order item details.
     * @return The updated order item.
     */
    @PutMapping("/{id}")
    public ResponseEntity<OrderItem> updateOrderItem(@PathVariable Long id, @RequestBody OrderItem orderItem) {
        orderItem.setId(id); // Ensure the ID in the order item matches the path variable
        OrderItem updatedOrderItem = orderItemService.updateOrderItem(orderItem);
        return ResponseEntity.ok(updatedOrderItem);
    }

    /**
     * Delete an order item by its ID.
     * @param id The ID of the order item to delete.
     * @return Response indicating the deletion result.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrderItem(@PathVariable Long id) {
        orderItemService.deleteOrderItem(id);
        return ResponseEntity.ok("Order item deleted successfully");
    }

    // Additional admin-specific methods (e.g., filter by order, get statistics) can be added here.
}
