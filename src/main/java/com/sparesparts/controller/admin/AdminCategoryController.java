package com.sparesparts.controller.admin;

import com.sparesparts.entity.Category;
import com.sparesparts.entity.Product;
import com.sparesparts.entity.SubCategory;
import com.sparesparts.service.CategoryService;
import com.sparesparts.service.ProductService;
import com.sparesparts.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/category")
public class AdminCategoryController {

    // Injecting the CategoryService using @Autowired
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SubCategoryService subCategoryService;
    @Autowired
    private ProductService productService;

    /**
     * Get all categories
     * This method will return a list of all categories stored in the database.
     * It uses the GET HTTP method.
     *
     * @return ResponseEntity containing a list of Category objects and HTTP status 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories(); // Fetch all categories
        return ResponseEntity.ok(categories); // Return the categories with HTTP 200
    }

    /**
     * Get category by ID
     * This method fetches a specific category based on the provided ID.
     * It uses the GET HTTP method and expects an ID as a path variable.
     *
     * @param id The ID of the category to retrieve
     * @return ResponseEntity containing the Category object if found, otherwise HTTP 404 (Not Found).
     */
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Optional<Category> category = categoryService.getCategoryById(id); // Fetch the category by ID
        // If category exists, return it with HTTP 200, otherwise return 404
        return category.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Create a new category
     * This method allows clients to create a new category by providing category data in the request body.
     * It uses the POST HTTP method.
     *
     * @param category The Category object to be created
     * @return ResponseEntity containing the created Category object and HTTP status 201 (Created).
     */
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category createdCategory = categoryService.saveCategory(category); // Save the new category
        // Return the created category with HTTP 201 (Created)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    /**
     * Update an existing category
     * This method allows clients to update an existing category by providing the ID and the new category data.
     * It uses the PUT HTTP method.
     *
     * @param id       The ID of the category to be updated
     * @param category The updated Category object
     * @return ResponseEntity containing the updated Category object and HTTP status 200 (OK).
     */
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        Optional<Category> updatedCategory = categoryService.updateCategory(id, category); // Update the category
        // If the category is updated, return it with HTTP 200, otherwise return 404
        return updatedCategory.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Delete a category
     * This method allows clients to delete a specific category by providing its ID.
     * It uses the DELETE HTTP method.
     *
     * @param id The ID of the category to be deleted
     * @return ResponseEntity with HTTP status 204 (No Content) if deletion is successful, or 404 if not found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        boolean deleted = categoryService.deleteCategoryById(id); // Delete the category
        // If deletion was successful, return HTTP 204 (No Content), otherwise return 404
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    /**
     * Delete a subcategory
     * This method allows clients to delete a specific subcategory by providing its ID.
     * It uses the DELETE HTTP method.
     *
     * @param id The ID of the subcategory to be deleted
     * @return ResponseEntity with HTTP status 204 (No Content) if deletion is successful, or 404 if not found.
     */
    @DeleteMapping("/subcategory/{id}")
    public ResponseEntity<Void> deleteSubcategory(@PathVariable Long id) {
        boolean deleted = subCategoryService.deleteSubCategoryById(id); // Delete the subcategory
        // If deletion was successful, return HTTP 204 (No Content), otherwise return 404
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }


    @GetMapping("/product/{id}")
    public ResponseEntity<List<Category>> getAllProductCategories(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);
        if (product.isPresent()) {
            List<Category> categories = product.get().getCategories();
            return ResponseEntity.ok(categories);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/sub/product/{id}")
    public ResponseEntity<List<SubCategory>> getAllProductSubCategories(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);
        if (product.isPresent()) {
            List<SubCategory> subCategories = product.get().getSubCategories();
            return ResponseEntity.ok(subCategories);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/upload-image")
    public ResponseEntity<Category> uploadCategoryImage(@RequestBody Category category)
    {
        Optional<Category> existingCategory = categoryService.getCategoryById(category.getId());
        if (existingCategory.isPresent()) {
            existingCategory.get().setCategoryImage(category.getCategoryImage());
            Category savedCategory = categoryService.saveCategory(existingCategory.get());
            return ResponseEntity.ok(savedCategory);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/upload-sub-category-image")
    public ResponseEntity<SubCategory> uploadSubcategoryImage(@RequestBody SubCategory subCategory){
        Optional<SubCategory> existingSubCategory = subCategoryService.getSubCategoryById(subCategory.getId());
        if (existingSubCategory.isPresent()) {
            existingSubCategory.get().setSubCategoryImage(subCategory.getSubCategoryImage());
            SubCategory savedSubCategory = subCategoryService.saveSubCategory(existingSubCategory.get());
            return ResponseEntity.ok(savedSubCategory);
        }
        return ResponseEntity.notFound().build();
    }
}
