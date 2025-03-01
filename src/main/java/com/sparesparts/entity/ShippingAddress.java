package com.sparesparts.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
public class ShippingAddress {
    @Id
    private Long id;

    private String recipientName;
    private String addressLine1;
    private String addressLine2; // Optional
    private String city;
    private String state;
    private String postalCode;
    private String mobile;
    private String email;

    @CreationTimestamp
    private LocalDateTime createdAt; // Creation timestamp
    @UpdateTimestamp
    private LocalDateTime updatedAt; // Update timestamp

}
