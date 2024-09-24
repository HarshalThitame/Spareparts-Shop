package com.sparesparts.repositories;

import com.sparesparts.entity.Category;
import com.sparesparts.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for managing {@link Product} entities.
 * Provides methods for standard CRUD operations and custom queries for products.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


    /**
     * Find products by name containing a search string (case insensitive).
     *
     * @param name The search string for the product name.
     * @return A list of products whose name contains the given string.
     */
    List<Product> findByNameContainingIgnoreCase(String name);


    /**
     * Find products by price within a specified range.
     *
     * @param minPrice The minimum price.
     * @param maxPrice The maximum price.
     * @return A list of products whose price is between minPrice and maxPrice.
     */
    List<Product> findByPriceBetween(double minPrice, double maxPrice);

    List<Product> findBySubCategoriesId(Long subCategoryId);
}
