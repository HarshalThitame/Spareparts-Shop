package com.sparesparts.service.serviceImpl;


import com.sparesparts.entity.OrderItem;
import com.sparesparts.repositories.OrderItemRepository;
import com.sparesparts.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the OrderItemService interface.
 */
@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    /**
     * Create a new order item.
     * @param orderItem The order item to create.
     * @return The created order item.
     */
    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    /**
     * Get an order item by ID.
     * @param id The ID of the order item to retrieve.
     * @return The order item with the specified ID.
     */
    @Override
    public OrderItem getOrderItemById(Long id) {
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderItem not found with ID: " + id));
    }

    /**
     * Update an existing order item.
     * @param orderItem The order item with updated details.
     * @return The updated order item.
     */
    @Override
    public OrderItem updateOrderItem(OrderItem orderItem) {
        // Check if order item exists
        if (!orderItemRepository.existsById(orderItem.getId())) {
            throw new RuntimeException("OrderItem not found with ID: " + orderItem.getId());
        }
        return orderItemRepository.save(orderItem);
    }

    /**
     * Delete an order item by ID.
     * @param id The ID of the order item to delete.
     */
    @Override
    public void deleteOrderItem(Long id) {
        if (!orderItemRepository.existsById(id)) {
            throw new RuntimeException("OrderItem not found with ID: " + id);
        }
        orderItemRepository.deleteById(id);
    }

    /**
     * Get all order items for a specific order.
     * @param orderId The ID of the order whose items to retrieve.
     * @return List of order items associated with the specified order.
     */
    @Override
    public List<OrderItem> getOrderItemsByOrderId(Long orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }

    @Override
    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }


}
