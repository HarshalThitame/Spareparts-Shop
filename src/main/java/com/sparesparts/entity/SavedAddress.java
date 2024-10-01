package com.sparesparts.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
public class SavedAddress {
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

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Ensure the user reference is set
    @JsonBackReference
    private User user; // Reference to the User entity


    @CreationTimestamp
    private LocalDateTime createdAt; // Creation timestamp
    @UpdateTimestamp
    private LocalDateTime updatedAt; // Update timestamp

}
