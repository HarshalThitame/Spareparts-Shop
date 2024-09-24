package com.sparesparts.service.serviceImpl;


import com.sparesparts.entity.SubCategory;
import com.sparesparts.repositories.SubCategoryRepository;
import com.sparesparts.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubCategoryServiceImpl implements SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;

    @Autowired
    public SubCategoryServiceImpl(SubCategoryRepository subCategoryRepository) {
        this.subCategoryRepository = subCategoryRepository;
    }

    @Override
    public List<SubCategory> getAllSubCategories() {
        return subCategoryRepository.findAll();
    }

    @Override
    public Optional<SubCategory> getSubCategoryById(Long id) {
        return subCategoryRepository.findById(id);
    }

    @Override
    public SubCategory saveSubCategory(SubCategory subCategory) {
        return subCategoryRepository.save(subCategory);
    }

    @Override
    public Optional<SubCategory> updateCategory(Long id, SubCategory subCategory) {
        if (!subCategoryRepository.existsById(id)) {
            return Optional.empty(); // Return empty if the category doesn't exist
        }
        subCategory.setId(id); // Set the ID to ensure it updates the correct entity
        return Optional.of(subCategoryRepository.save(subCategory));
    }

    @Override
    public boolean deleteSubCategoryById(Long id) {
        if (subCategoryRepository.existsById(id)) {
            subCategoryRepository.deleteById(id);
            return true; // Return true if deletion was successful
        }
        return false; // Return false if the category wasn't found
    }

    @Override
    public List<SubCategory> findByCategoryId(Long categoryId) {
        return subCategoryRepository.findByCategoryId(categoryId);
    }
}
