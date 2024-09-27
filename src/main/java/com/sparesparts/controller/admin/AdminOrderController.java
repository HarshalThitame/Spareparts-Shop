package com.sparesparts.controller.admin;


import com.sparesparts.entity.Order;
import com.sparesparts.entity.Product;
import com.sparesparts.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing orders by the admin.
 */
@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {

    private final OrderService orderService;

    @Autowired
    public AdminOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Get a list of all orders.
     * @return List of all orders.
     */
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    /**
     * Get an order by its ID.
     * @param id The ID of the order to retrieve.
     * @return The order with the specified ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    /**
     * Update an existing order.
     * @param id The ID of the order to update.
     * @param order The updated order details.
     * @return The updated order.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order order) {
        order.setId(id); // Ensure the ID in the order matches the path variable
        Order updatedOrder = orderService.updateOrder(order);
        return ResponseEntity.ok(updatedOrder);
    }

    /**
     * Delete an order by its ID.
     * @param id The ID of the order to delete.
     * @return Response indicating the deletion result.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Order> deleteOrder(@PathVariable Long id) {
        Order orderById = orderService.getOrderById(id);
        if (orderById != null) {
            orderService.deleteOrder(id);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/marked-as-viewed/{id}")
    public ResponseEntity<Order> markedAsViewd(@PathVariable Long id){
        Order order = orderService.markedAsViewed(id);

        return ResponseEntity.ok(order);

    }

    @PutMapping("/update-order")
    public ResponseEntity<Order> placeOrder(@RequestBody Order order) {
        Order placedOrder = orderService.updateOrder(order); // Implement method to place a new order
        return ResponseEntity.ok(placedOrder);
    }


}
