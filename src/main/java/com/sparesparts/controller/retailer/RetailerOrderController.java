package com.sparesparts.controller.retailer;

import com.sparesparts.config.mail.EmailData;
import com.sparesparts.config.mail.EmailService;
import com.sparesparts.entity.Order;
import com.sparesparts.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/retailer/orders")
public class RetailerOrderController {

    @Autowired
    private EmailService emailService;

    private final OrderService orderService;

    @Autowired
    public RetailerOrderController(OrderService orderService) {
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
    // Get all orders
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    // Get order by ID
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    // Create a new order
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order createdOrder = orderService.createOrder(order);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @PostMapping("/send-order-mail")
    public ResponseEntity<?> sendOrderPlaceMail(@RequestBody EmailData emailData){
        emailService.sendDataToUser(emailData.getTo(),emailData.getSubject(),emailData.getBody());
        return ResponseEntity.ok().build();
    }

    // Update an existing order
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order order) {
        order.setId(id); // Ensure the ID is set for update
        Order updatedOrder = orderService.updateOrder(order);
        return ResponseEntity.ok(updatedOrder);
    }

    // Delete an order
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Cancel an existing order for the authenticated retailer.
     * @param id The ID of the order to cancel.
     * @return Response indicating the cancellation result.
     */
    @DeleteMapping("/{id}/cancel")
    public ResponseEntity<String> cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id); // Implement method to cancel an order
        return ResponseEntity.ok("Order canceled successfully");
    }

}
