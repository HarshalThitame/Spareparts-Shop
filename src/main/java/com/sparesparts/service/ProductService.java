package com.sparesparts.service;

import com.sparesparts.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
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

    /**
     * Search for products by brand ID, brand model ID, and category ID.
     *
     * @param brandId      the ID of the brand
     * @param brandModelId the ID of the brand model
     * @param categoryId   the ID of the category
     * @return a list of products matching the specified criteria
     */
    List<Product> searchByBrandsAndBrandModelAndCategory(Long brandId, Long brandModelId, Long categoryId);

    /**
     * Search for products by brand ID and brand model ID.
     *
     * @param brandId      the ID of the brand
     * @param brandModelId the ID of the brand model
     * @return a list of products matching the specified criteria
     */
    List<Product> searchByBrandAndModel(Long brandId, Long brandModelId);

    /**
     * Search for products by brand ID and category ID.
     *
     * @param brandId    the ID of the brand
     * @param categoryId the ID of the category
     * @return a list of products matching the specified criteria
     */
    List<Product> searchByBrandAndCategory(Long brandId, Long categoryId);

    /**
     * Search for products by brand model ID and category ID.
     *
     * @param brandModelId the ID of the brand model
     * @param categoryId   the ID of the category
     * @return a list of products matching the specified criteria
     */
    List<Product> searchByModelAndCategory(Long brandModelId, Long categoryId);

    /**
     * Search for products by brand ID.
     *
     * @param brandId the ID of the brand
     * @return a list of products matching the specified criteria
     */
    List<Product> searchByBrand(Long brandId);

    /**
     * Search for products by brand model ID.
     *
     * @param brandModelId the ID of the brand model
     * @return a list of products matching the specified criteria
     */
    List<Product> searchByModel(Long brandModelId);

    /**
     * Search for products by category ID.
     *
     * @param categoryId the ID of the category
     * @return a list of products matching the specified criteria
     */
    List<Product> searchByCategory(Long categoryId);


    public Map<String, Object> saveProductsFromCSV(MultipartFile file);

    List<Product> getLowStockProducts(); // Method to retrieve low stock products
    List<Product> getDeadProducts(); // Method to retrieve dead products
    List<Product> getRecentlyUpdatedProducts(int i); // Method to retrieve recently updated products

    List<Product> searchProductsByKeywords(String keyword);

    public List<Product> getTopSellingProducts(int limit);
    public List<Product> getTop18Products();
    public Page<Product> getPaginatedProducts(int page, int size);
}

