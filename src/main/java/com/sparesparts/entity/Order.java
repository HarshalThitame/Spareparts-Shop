package com.sparesparts.entity;

import com.sparesparts.entity.Enum.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")  // Changed table name to avoid SQL reserved words
@Data
public class Order {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Reference to the User entity

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // Enum for order status (e.g., PENDING, PAID, SHIPPED, etc.)

    private double totalAmount; // Total amount for the order

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "shipping_address_id", nullable = false)
//    private ShippingAddress shippingAddress; // Shipping address for the order

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems; // List of order items

    private double discountAmount; // Total discount applied to the order

    private double shippingCost; // Cost of shipping

    @CreationTimestamp
    private LocalDateTime createdAt; // Timestamp for when the order was created

    @UpdateTimestamp
    private LocalDateTime updatedAt; // Timestamp for when the order was last updated

    private String cancellationReason; // Reason for cancellation (if applicable)
    private String notes; // Additional notes or comments regarding the order
    private Boolean isVor = false;
    private Boolean isViewed = false; // New field to track if the order is viewed


    // Additional fields as necessary
}
