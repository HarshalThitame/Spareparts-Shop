package com.sparesparts.service;

import com.sparesparts.entity.Category;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing {@link Category} entities.
 */
public interface CategoryService {

    /**
     * Get all categories.
     *
     * @return A list of all categories.
     */
    List<Category> getAllCategories();

    /**
     * Get a category by its ID.
     *
     * @param id The ID of the category.
     * @return The category with the given ID.
     */
    Optional<Category> getCategoryById(Long id);

    /**
     * Save or update a category.
     *
     * @param category The category to save.
     * @return The saved category.
     */
    Category saveCategory(Category category);

    /**
     * Update an existing category.
     *
     * @param id       The ID of the category to update.
     * @param category The category data to update.
     * @return The updated category.
     */
    Optional<Category> updateCategory(Long id, Category category);

    /**
     * Delete a category by its ID.
     *
     * @param id The ID of the category.
     * @return
     */
    boolean deleteCategoryById(Long id);
}
