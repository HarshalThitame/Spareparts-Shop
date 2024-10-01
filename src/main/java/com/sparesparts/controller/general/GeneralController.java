package com.sparesparts.controller.general;

import com.sparesparts.entity.*;
import com.sparesparts.service.*;
import com.sparesparts.services.BrandService;
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
    private final BrandService brandService;
    private final BrandModelService brandModelService;
    @Autowired
    public GeneralController(CategoryService categoryService, ReviewService reviewService, ProductService productService, SubCategoryService subCategoryService, com.sparesparts.services.BrandService brandService, BrandModelService brandModelService) {
        this.categoryService = categoryService;
        this.reviewService = reviewService;
        this.productService = productService;
        this.subCategoryService = subCategoryService;
        this.brandService = brandService;
        this.brandModelService = brandModelService;
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
     * Get all brands.
     *
     * @return List of brands.
     */
    @GetMapping("/brands")
    public ResponseEntity<List<Brand>> getAllBrands() {
        List<Brand> brands = brandService.getAllBrands(); // Fetch all brands from the service
        return ResponseEntity.ok(brands); // Return the list of brands with a 200 OK status
    }

    /**
     * Get all brand models.
     *
     * @return List of brand models.
     */
    @GetMapping("/brand-models")
    public ResponseEntity<List<BrandModel>> getAllBrandModels() {
        List<BrandModel> brandModels = brandModelService.getAllBrandModels(); // Fetch all brand models from the service
        return ResponseEntity.ok(brandModels); // Return the list of brand models with a 200 OK status
    }
    /**
     * Get all BrandModels by Brand ID.
     *
     * @param brandId The ID of the brand.
     * @return List of BrandModels associated with the brand.
     */
    @GetMapping("/brand-models/{brandId}")
    public ResponseEntity<List<BrandModel>> getBrandModelsByBrandId(@PathVariable Long brandId) {
        List<BrandModel> brandModels = brandModelService.getBrandModelsByBrandId(brandId);
        if (brandModels.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 if no models found
        }
        return ResponseEntity.ok(brandModels); // Return the list of brand models
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

    /**
     * Search for products by brand ID, brand model ID, and category ID.
     *
     * @param brandId      the ID of the brand
     * @param brandModelId the ID of the brand model
     * @param categoryId   the ID of the category
     * @return a list of products matching the specified criteria
     */
    @GetMapping("/searchByBrandsAndBrandModelAndCategory/{brandId}/{brandModelId}/{categoryId}")
    public List<Product> searchByBrandsAndBrandModelAndCategory(
            @PathVariable Long brandId,
            @PathVariable Long brandModelId,
            @PathVariable Long categoryId) {
        return productService.searchByBrandsAndBrandModelAndCategory(brandId, brandModelId, categoryId);
    }

    /**
     * Search for products by brand ID and brand model ID.
     *
     * @param brandId      the ID of the brand
     * @param brandModelId the ID of the brand model
     * @return a list of products matching the specified criteria
     */
    @GetMapping("/searchByBrandAndModel/{brandId}/{brandModelId}")
    public List<Product> searchByBrandAndModel(
            @PathVariable Long brandId,
            @PathVariable Long brandModelId) {
        return productService.searchByBrandAndModel(brandId, brandModelId);
    }

    /**
     * Search for products by brand ID and category ID.
     *
     * @param brandId    the ID of the brand
     * @param categoryId the ID of the category
     * @return a list of products matching the specified criteria
     */
    @GetMapping("/searchByBrandAndCategory/{brandId}/{categoryId}")
    public List<Product> searchByBrandAndCategory(
            @PathVariable Long brandId,
            @PathVariable Long categoryId) {
        return productService.searchByBrandAndCategory(brandId, categoryId);
    }

    /**
     * Search for products by brand model ID and category ID.
     *
     * @param brandModelId the ID of the brand model
     * @param categoryId   the ID of the category
     * @return a list of products matching the specified criteria
     */
    @GetMapping("/searchByModelAndCategory/{brandModelId}/{categoryId}")
    public List<Product> searchByModelAndCategory(
            @PathVariable Long brandModelId,
            @PathVariable Long categoryId) {
        return productService.searchByModelAndCategory(brandModelId, categoryId);
    }

    /**
     * Search for products by brand ID.
     *
     * @param brandId the ID of the brand
     * @return a list of products matching the specified criteria
     */
    @GetMapping("/searchByBrand/{brandId}")
    public List<Product> searchByBrand(
            @PathVariable Long brandId) {
        return productService.searchByBrand(brandId);
    }

    /**
     * Search for products by brand model ID.
     *
     * @param brandModelId the ID of the brand model
     * @return a list of products matching the specified criteria
     */
    @GetMapping("/searchByModel/{brandModelId}")
    public List<Product> searchByModel(
            @PathVariable Long brandModelId) {
        return productService.searchByModel(brandModelId);
    }

    /**
     * Search for products by category ID.
     *
     * @param categoryId the ID of the category
     * @return a list of products matching the specified criteria
     */
    @GetMapping("/searchByCategory/{categoryId}")
    public List<Product> searchByCategory(
            @PathVariable Long categoryId) {
        return productService.searchByCategory(categoryId);
    }

}
