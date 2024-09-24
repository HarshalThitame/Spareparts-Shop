package com.sparesparts.service;

import com.sparesparts.entity.Product;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing {@link Product} entities.
 * Provides methods to perform business logic operations on products.
 */
public interface ProductService {

    /**
     * Retrieve all products.
     *
     * @return A list of all products.
     */
    List<Product> getAllProducts();

    /**
     * Retrieve a product by its ID.
     *
     * @param id The ID of the product.
     * @return An Optional containing the product if found, or empty otherwise.
     */
    Optional<Product> getProductById(Long id);

    /**
     * Save or update a product.
     *
     * @param product The product entity to be saved.
     * @return The saved product entity.
     */
    Product saveProduct(Product product);

    /**
     * Delete a product by its ID.
     *
     * @param id The ID of the product to be deleted.
     */
    void deleteProductById(Long id);


    /**
     * Search products by their name.
     *
     * @param name The name or part of the name of the product.
     * @return A list of products whose names contain the search string.
     */
    List<Product> searchProductsByName(String name);

    List<Product> saveMultipleProducts(List<Product> products);

    List<Product> getProductsBySubCategoryId(Long subCategoryId);
}

