package com.sparesparts.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
public class ShippingAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String recipientName;
    private String addressLine1;
    private String addressLine2; // Optional
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String mobile;
    private String email;

    // Optionally link to the customer (user) if needed
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // Assuming you have a User entity
    @CreationTimestamp
    private LocalDateTime createdAt; // Creation timestamp
    @UpdateTimestamp
    private LocalDateTime updatedAt; // Update timestamp

}
