package com.sparesparts.controller.admin;


import com.sparesparts.config.mail.EmailData;
import com.sparesparts.config.mail.EmailService;
import com.sparesparts.entity.Enum.OrderStatus;
import com.sparesparts.entity.Order;
import com.sparesparts.entity.Product;
import com.sparesparts.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing orders by the admin.
 */
@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {

    @Autowired
    public EmailService emailService;

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

    @PostMapping("/send-order-mail")
    public ResponseEntity<?> sendOrderPlaceMail(@RequestBody EmailData emailData){
        emailService.sendDataToUser(emailData.getTo(),emailData.getSubject(),emailData.getBody());
        return ResponseEntity.ok().build();
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
    public ResponseEntity<Order> markedAsViewed(@PathVariable Long id){
        Order order = orderService.markedAsViewed(id);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/marked-as-un-viewed/{id}")
    public ResponseEntity<Order> markedAsUnViewed(@PathVariable Long id){
        Order order = orderService.markedAsUnViewed(id);
        return ResponseEntity.ok(order);
    }

    @PutMapping("/update-order")
    public ResponseEntity<Order> placeOrder(@RequestBody Order order) {
        Order placedOrder = orderService.updateOrder(order); // Implement method to place a new order
        return ResponseEntity.ok(placedOrder);
    }

    @GetMapping("/new-orders")
    public List<Order> getUnseenOrders() {
        return orderService.getUnseenOrders();
    }

    @GetMapping("/new-order-count")
    public long getUnseenOrderCount() {
        return orderService.getUnseenOrderCount();
    }

    @GetMapping("/by-pagination")
    public Page<Order> getOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return orderService.getAllOrders(page, size);
    }

    @GetMapping("/by-pagination/is-vor")
    public Page<Order> getIsVorOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return orderService.getVorOrders(page, size);
    }

    // Get paginated orders by status
    @GetMapping("/status/by-pagination")
    public Page<Order> getOrdersByStatus(
            @RequestParam OrderStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return orderService.getOrdersByStatus(status, page, size);
    }
    @GetMapping("/cancelled")
    public Page<Order> getCancelledOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return orderService.getOrdersByStatus(OrderStatus.CANCELLED, page, size);
    }

    @GetMapping("/unpaid")
    public Page<Order> getUnpaidOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return orderService.getOrdersByStatus(OrderStatus.UNPAID, page, size);
    }
}
