package com.sparesparts.service;

import com.sparesparts.entity.Category;
import com.sparesparts.entity.SubCategory;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing {@link Category} entities.
 */
public interface SubCategoryService {

    /**
     * Get all categories.
     *
     * @return A list of all subcategories.
     */
    List<SubCategory> getAllSubCategories();

    /**
     * Get a subcategory by its ID.
     *
     * @param id The ID of the subcategory.
     * @return The subcategory with the given ID.
     */
    Optional<SubCategory> getSubCategoryById(Long id);

    /**
     * Save or update a category.
     *
     * @param subCategory The category to save.
     * @return The saved subcategory.
     */
    SubCategory saveSubCategory(SubCategory subCategory);

    /**
     * Update an existing category.
     *
     * @param id       The ID of the category to update.
     * @param subCategory The category data to update.
     * @return The updated category.
     */
    Optional<SubCategory> updateCategory(Long id, SubCategory subCategory);

    /**
     * Delete a subcategory by its ID.
     *
     * @param id The ID of the subcategory.
     * @return
     */
    boolean deleteSubCategoryById(Long id);
    /**
     * Find all subcategories by the parent category ID.
     *
     * @param categoryId The ID of the category.
     * @return List of subcategories under the specified category.
     */
    public List<SubCategory> findByCategoryId(Long categoryId);
}
