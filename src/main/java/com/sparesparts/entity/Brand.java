package com.sparesparts.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;  // Brand name

    // One-to-Many relationship with BrandModel
    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
    private List<BrandModel> brandModels;

    // Many-to-Many relationship with Product
    @ManyToMany(mappedBy = "brands")
    @JsonIgnore
    private List<Product> products;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;
}
