package com.sparesparts.service.serviceImpl;

import com.sparesparts.entity.Category;
import com.sparesparts.entity.SubCategory;
import com.sparesparts.repositories.CategoryRepository;
import com.sparesparts.repositories.SubCategoryRepository;
import com.sparesparts.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@link CategoryService}.
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, SubCategoryRepository subCategoryRepository) {
        this.categoryRepository = categoryRepository;
        this.subCategoryRepository = subCategoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category saveCategory(Category category) {
        // Set the parent category for each subcategory
        if (category.getSubCategories() != null) {
            for (SubCategory subcategory : category.getSubCategories()) {
                subcategory.setCategory(category); // Set the parent category
            }
        }
        return categoryRepository.save(category);
    }


    @Override
    public boolean deleteCategoryById(Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return true; // Return true if the category was deleted successfully
        } else {
            return false; // Return false if the category does not exist
        }
    }


    @Override
    public Optional<Category> updateCategory(Long id, Category category) {
        System.out.println(category);

        // Check if category exists by ID
        return categoryRepository.findById(id).map(existingCategory -> {
            // Update fields of existing category
            existingCategory.setName(category.getName());
            existingCategory.setDescription(category.getDescription());

            // Update subcategories
            if (category.getSubCategories() != null) {
                // Clear existing subcategories if you want to replace them
                existingCategory.getSubCategories().clear();

                for (SubCategory subcategory : category.getSubCategories()) {
                    if (subcategory.getId() != null) {
                        // If subcategory has an ID, update it
                        Optional<SubCategory> existingSubcategory = subCategoryRepository.findById(subcategory.getId());
                        existingSubcategory.ifPresent(existing -> {
                            existing.setName(subcategory.getName());
                            existing.setDescription(subcategory.getDescription());
                            existing.setCategory(existingCategory); // Update parent reference if needed
                        });
                    } else {
                        // If it doesn't have an ID, treat it as a new subcategory
                        subcategory.setCategory(existingCategory); // Set parent category
                        existingCategory.getSubCategories().add(subcategory);
                    }
                }
            }

            // Save the updated category
            return categoryRepository.save(existingCategory);
        });
    }


}
