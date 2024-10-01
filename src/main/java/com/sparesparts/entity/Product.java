package com.sparesparts.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Product {
    @Id
    private Long id;

    private String name;
    private String description;
    private double price;

    @Column(nullable = false, unique = true)
    private String partNumber;  // Unique identifier for spare parts

    private int moq = 1;
    private String binLocation;
    private double discountOnPurchase = 0;
    private double discountToCounter = 0;
    private double discountToMechanics = 0;
    private double discountToRetailer = 0;
    private int stockQuantity;
    private String mainImage;
    private double weight;
    private String dimensions;  // Dimensions of the part
    private String material;    // Material of the product
    private boolean isPublishedForCustomer = false;
    private boolean isPublishedForRetailer = false;
    private boolean isPublishedForMechanic = false;
    private boolean isBlocked = false;
    private double gst = 0.0;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Images> images;

    // Many-to-Many relationship with Brand
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "product_brands",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "brand_id")
    )
    private List<Brand> brands;

    // Many-to-Many relationship with BrandModel
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "product_brand_models",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "brand_model_id")
    )
    private List<BrandModel> brandModels;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "product_categories",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "product_sub_categories",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "sub_category_id")
    )
    private List<SubCategory> subCategories;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<OrderItem> orderItems; // List of order items for this product

    @ManyToMany(mappedBy = "products")
    @JsonIgnore
    private List<Offer> offers;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "product")
    private List<Review> reviews;

    public Product(String name, String description, double price, String partNumber, int moq,
                   int stockQuantity, double weight, String dimensions, String material,
                   String mainImage, boolean isPublishedForCustomer) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.partNumber = partNumber;
        this.moq = moq;
        this.stockQuantity = stockQuantity;
        this.weight = weight;
        this.dimensions = dimensions;
        this.material = material;
        this.mainImage = mainImage;
        this.isPublishedForCustomer = isPublishedForCustomer;
    }
    public Product() {

    }
}
