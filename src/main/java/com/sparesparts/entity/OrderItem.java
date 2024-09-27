package com.sparesparts.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "order_items") // Changed table name for clarity and to avoid SQL reserved words
@Data
public class OrderItem {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnore
    private Order order; // Reference to the associated Order entity

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product; // Reference to the associated Product entity

    private int quantity; // Quantity of the product ordered

    private double price; // Price of the product at the time of the order

    private double subtotal; // Total cost before any discounts

    private double discountAmount; // Discount applied to this item

    private double totalPrice; // Final price after discount

    private double taxAmount = 0.0; // Tax applied to this item

    @CreationTimestamp
    private LocalDateTime createdAt; // Automatically generated creation timestamp

    @UpdateTimestamp
    private LocalDateTime updatedAt; // Automatically generated update timestamp


}
