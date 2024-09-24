package com.sparesparts.controller;

import com.sparesparts.entity.Category;
import com.sparesparts.entity.Product;
import com.sparesparts.entity.Review;
import com.sparesparts.entity.SubCategory;
import com.sparesparts.service.CategoryService;
import com.sparesparts.service.ProductService;
import com.sparesparts.service.ReviewService;
import com.sparesparts.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for general actions that are accessible to all users.
 * No authentication is required for these endpoints.
 */
@RestController
@RequestMapping("/api/general")
public class GeneralController {

    private final CategoryService categoryService;
    private final ReviewService reviewService;
    private final ProductService productService;
    private final SubCategoryService subCategoryService;
    @Autowired
    public GeneralController(CategoryService categoryService, ReviewService reviewService, ProductService productService, SubCategoryService subCategoryService) {
        this.categoryService = categoryService;
        this.reviewService = reviewService;
        this.productService = productService;
        this.subCategoryService = subCategoryService;
    }

    /**
     * Get all categories.
     *
     * @return List of categories.
     */
    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    /**
     * Get a category by its ID.
     *
     * @param id The ID of the category.
     * @return The category with the given ID.
     */
    @GetMapping("/category/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Optional<Category> category = categoryService.getCategoryById(id);
        return category.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    /**
     * Get reviews for a product.
     *
     * @param productId The ID of the product.
     * @return List of reviews for the product.
     */
    @GetMapping("/reviews/{productId}")
    public ResponseEntity<List<Review>> getReviewsByProductId(@PathVariable Long productId) {
        return ResponseEntity.ok(reviewService.getReviewsByProductId(productId));
    }

    /**
     * Add a review for a product.
     *
     * @param review The review to be added.
     * @return The saved review.
     */
    @PostMapping("/review")
    public ResponseEntity<Review> addReview(@RequestBody Review review) {
        return ResponseEntity.ok(reviewService.saveReview(review));
    }

    /**
     * Get all products.
     *
     * @return List of products.
     */
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    /**
     * Get all subcategories by category ID.
     *
     * @param categoryId The ID of the category.
     * @return List of subcategories.
     */
    @GetMapping("/subCategories/{categoryId}")
    public ResponseEntity<List<SubCategory>> getSubcategoriesByCategoryId(@PathVariable Long categoryId) {
        return ResponseEntity.ok(subCategoryService.findByCategoryId(categoryId));
    }

    /**
     * Get a product by its ID.
     *
     * @param id The ID of the product.
     * @return The product with the given ID.
     */
    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Get all products by subcategory ID.
     *
     * @param subCategoryId The ID of the subcategory.
     * @return List of products belonging to the specified subcategory.
     */
    @GetMapping("/products/by-sub-category/{subCategoryId}")
    public ResponseEntity<List<Product>> getProductsBySubCategoryId(@PathVariable Long subCategoryId) {
        List<Product> products = productService.getProductsBySubCategoryId(subCategoryId);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 if no products found
        }
        return ResponseEntity.ok(products); // Return the list of products
    }
}
