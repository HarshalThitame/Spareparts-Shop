package com.sparesparts.service.serviceImpl;


import com.sparesparts.entity.Enum.OrderStatus;
import com.sparesparts.entity.Enum.Role;
import com.sparesparts.entity.Order;
import com.sparesparts.entity.OrderItem;
import com.sparesparts.entity.Product;
import com.sparesparts.entity.ShippingAddress;
import com.sparesparts.repositories.OrderItemRepository;
import com.sparesparts.repositories.OrderRepository;
import com.sparesparts.repositories.ProductRepository;
import com.sparesparts.repositories.ShippingAddressRepository;
import com.sparesparts.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the OrderService interface.
 */
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ShippingAddressRepository shippingAddressRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository, ShippingAddressRepository shippingAddressRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.shippingAddressRepository = shippingAddressRepository;
        this.productRepository = productRepository;
    }

    /**
     * Create a new order.
     *
     * @param order The order to create.
     * @return The created order.
     */
    @Transactional
    @Override
    public Order createOrder(Order order) {
        // Check if the shipping address needs to be saved
        ShippingAddress shippingAddress = order.getShippingAddress();
        if (shippingAddress != null) {
            // Save the shipping address
            shippingAddress.setId(System.currentTimeMillis()-1000000);
            shippingAddress = shippingAddressRepository.save(shippingAddress);
            order.setShippingAddress(shippingAddress); // Set the saved shipping address back to the order
        }

        // Set the saved items to the order
        List<OrderItem> orderItems = order.getOrderItems();
        order.setOrderItems(null);
        order.setCreatedAt(LocalDateTime.now());
        Order savedOrder = orderRepository.save(order);

        double tp = 0.0;
        double td = 0.0;
        List<OrderItem> newOrderItems = new ArrayList<>();
        for (OrderItem item : orderItems) {
            item.setId(System.currentTimeMillis());

            item.setSubtotal(item.getProduct().getPrice()*item.getQuantity());

            if (order.getUser().getUserRole() == Role.CUSTOMER) {
                item.setDiscountAmount(calculateDiscount(item.getProduct().getPrice(), item.getProduct().getDiscountOnPurchase(), item.getQuantity()));
                double discountOfProduct = item.getProduct().getPrice()- calculateDiscount(item.getProduct().getPrice(), item.getProduct().getDiscountOnPurchase(), 1);
                item.setTaxAmount((discountOfProduct*item.getQuantity())*(item.getProduct().getGst()/100));
                item.setTotalPrice(((item.getProduct().getPrice()*item.getQuantity()) -(item.getProduct().getPrice()*item.getQuantity())*(item.getProduct().getDiscountOnPurchase()/100))+item.getTaxAmount());
                //calculate gst
            } else if (order.getUser().getUserRole() == Role.RETAILER) {
                item.setDiscountAmount(calculateDiscount(item.getProduct().getPrice(), item.getProduct().getDiscountToRetailer(), item.getQuantity()));
                double discountOfProduct = item.getProduct().getPrice()- calculateDiscount(item.getProduct().getPrice(), item.getProduct().getDiscountToRetailer(), 1);
                item.setTaxAmount((discountOfProduct*item.getQuantity())*(item.getProduct().getGst()/100));
                item.setTotalPrice(((item.getProduct().getPrice()*item.getQuantity()) -(item.getProduct().getPrice()*item.getQuantity())*(item.getProduct().getDiscountToRetailer()/100))+item.getTaxAmount());

            } else if (order.getUser().getUserRole() == Role.MECHANIC) {
                item.setDiscountAmount(calculateDiscount(item.getProduct().getPrice(), item.getProduct().getDiscountToMechanics(), item.getQuantity()));
                double discountOfProduct = item.getProduct().getPrice()- calculateDiscount(item.getProduct().getPrice(), item.getProduct().getDiscountToMechanics(), 1);
                item.setTaxAmount((discountOfProduct*item.getQuantity())*(item.getProduct().getGst()/100));
                item.setTotalPrice(((item.getProduct().getPrice()*item.getQuantity()) -(item.getProduct().getPrice()*item.getQuantity())*(item.getProduct().getDiscountToMechanics()/100))+item.getTaxAmount());

            }

            item.getProduct().setCategories(null);

            tp += item.getTotalPrice();
            td += item.getDiscountAmount();

            item.setOrder(savedOrder);
            OrderItem savedOrderItem = orderItemRepository.save(item);
            newOrderItems.add(savedOrderItem);
        }
        savedOrder.setOrderItems(newOrderItems);

        order.setTotalAmount(tp);
        order.setDiscountAmount(td);

        orderRepository.save(order);
        return savedOrder;
    }

    /**
     * Update an existing order.
     *
     * @param order The order with updated details.
     * @return The updated order.
     */
    @Override
    public Order updateOrder(Order order) {
        // Check if the order exists
        if (!orderRepository.existsById(order.getId())) {
            throw new RuntimeException("Order not found with ID: " + order.getId());
        }

        // Fetch the existing order from the database
        Order existingOrder = orderRepository.findById(order.getId())
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + order.getId()));

        // Update relevant fields


        List<OrderItem> orderItems = order.getOrderItems();

        double tp = 0.0;
        double td = 0.0;
        for (OrderItem item : orderItems) {
            item.setSubtotal(item.getProduct().getPrice()*item.getQuantity());

            if (order.getUser().getUserRole() == Role.CUSTOMER) {
                item.setDiscountAmount(calculateDiscount(item.getProduct().getPrice(), item.getProduct().getDiscountOnPurchase(), item.getQuantity()));
                double discountOfProduct = item.getProduct().getPrice()- calculateDiscount(item.getProduct().getPrice(), item.getProduct().getDiscountOnPurchase(), 1);
                item.setTaxAmount((discountOfProduct*item.getQuantity())*(item.getProduct().getGst()/100));
                item.setTotalPrice(((item.getProduct().getPrice()*item.getQuantity()) -(item.getProduct().getPrice()*item.getQuantity())*(item.getProduct().getDiscountOnPurchase()/100))+item.getTaxAmount());
                //calculate gst
            } else if (order.getUser().getUserRole() == Role.RETAILER) {
                item.setDiscountAmount(calculateDiscount(item.getProduct().getPrice(), item.getProduct().getDiscountToRetailer(), item.getQuantity()));
                double discountOfProduct = item.getProduct().getPrice()- calculateDiscount(item.getProduct().getPrice(), item.getProduct().getDiscountToRetailer(), 1);
                item.setTaxAmount((discountOfProduct*item.getQuantity())*(item.getProduct().getGst()/100));
                item.setTotalPrice(((item.getProduct().getPrice()*item.getQuantity()) -(item.getProduct().getPrice()*item.getQuantity())*(item.getProduct().getDiscountToRetailer()/100))+item.getTaxAmount());

            } else if (order.getUser().getUserRole() == Role.MECHANIC) {
                item.setDiscountAmount(calculateDiscount(item.getProduct().getPrice(), item.getProduct().getDiscountToMechanics(), item.getQuantity()));
                double discountOfProduct = item.getProduct().getPrice()- calculateDiscount(item.getProduct().getPrice(), item.getProduct().getDiscountToMechanics(), 1);
                item.setTaxAmount((discountOfProduct*item.getQuantity())*(item.getProduct().getGst()/100));
                item.setTotalPrice(((item.getProduct().getPrice()*item.getQuantity()) -(item.getProduct().getPrice()*item.getQuantity())*(item.getProduct().getDiscountToMechanics()/100))+item.getTaxAmount());

            }

            item.getProduct().setCategories(null);

            tp += item.getTotalPrice();
            td += item.getDiscountAmount();
        }


        existingOrder.setTotalAmount(tp);
        existingOrder.setDiscountAmount(td);


        existingOrder.setCreatedAt(existingOrder.getCreatedAt());
        existingOrder.setStatus(order.getStatus());
        existingOrder.setIsVor(order.getIsVor());
        existingOrder.setIsViewed(order.getIsViewed());
        existingOrder.setNotes(order.getNotes());
        existingOrder.setCancellationReason(order.getCancellationReason());


        // Clear current order items to avoid orphan references
        existingOrder.getOrderItems().clear();


        // Add new order items
        for (OrderItem orderItem : order.getOrderItems()) {
            orderItem.setOrder(existingOrder); // Set the order reference
            existingOrder.getOrderItems().add(orderItem); // Add to the existing order's items
        }
        // Save the updated order
        return orderRepository.save(existingOrder);
    }


    /**
     * Get an order by ID.
     *
     * @param id The ID of the order to retrieve.
     * @return The order with the specified ID.
     */
    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + id));
    }


    /**
     * Delete an order by ID.
     *
     * @param id The ID of the order to delete.
     */
    @Override
    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("Order not found with ID: " + id);
        }
        orderRepository.deleteById(id);
    }

    /**
     * Get all orders for a specific user.
     *
     * @param userId The ID of the user whose orders to retrieve.
     * @return List of orders associated with the user.
     */
    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    /**
     * Get all orders with a specific status.
     *
     * @param status The status of the orders to retrieve.
     * @return List of orders with the specified status.
     */
    @Override
    public List<Order> getOrdersByStatus(String status) {
        return orderRepository.findByStatus(status);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getAllCustomerOrders() {
        // Fetch all orders for the authenticated customer
        return orderRepository.findAll(); // Adjust to filter orders based on authenticated user
    }

    @Override
    public Order getCustomerOrderById(Long id) {
        // Fetch a specific order by ID
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found")); // Handle order not found
    }

    @Override
    public Order placeOrder(Order order) {
        // Place a new order and save it to the database

        return orderRepository.save(order);
    }

    @Override
    public void cancelOrder(Long id) {
        // Cancel an existing order
        Order order = getCustomerOrderById(id);
        // Add logic to handle cancellation, e.g., updating status
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    @Override
    public Order markedAsViewed(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found with ID: " + id));
        if (order == null) {
            return new Order();
        }
        order.setIsViewed(true);

        return orderRepository.save(order);
    }

    @Override
    public Order markedAsUnViewed(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found with ID: " + id));
        if (order == null) {
            return new Order();
        }
        order.setIsViewed(false);
        return orderRepository.save(order);    }


    public double calculateDiscount(double price, double discount, int quantity) {
        return ((price * discount) / 100) * quantity;
    }

    @Override
    public List<Order> getOrderUpdates() {
        LocalDateTime recentThreshold = LocalDateTime.now().minusDays(7); // Change to desired timeframe
        return orderRepository.findUpdatedRecently(recentThreshold); // Assuming you have this method in your repository
    }

    @Override
    public List<Order> getUnseenOrders() {
        return orderRepository.findByIsViewedFalse(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    @Override
    public long getUnseenOrderCount() {
        return orderRepository.countByIsViewedFalse();
    }

    @Override
    public Page<Order> getAllOrders(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return orderRepository.findAll(pageable);
    }

    @Override
    public Page<Order> getOrdersByStatus(String status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return orderRepository.findByStatus(status, pageable);
    }
}
