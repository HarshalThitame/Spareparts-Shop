package com.sparesparts.repositories;

import com.sparesparts.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {

    /**
     * Find a subcategory by its name.
     *
     * @param name The name of the subcategory.
     * @return The subcategory with the specified name.
     */
    Optional<SubCategory> findByName(String name);

    /**
     * Check if a subcategory exists by its name.
     *
     * @param name The name of the subcategory.
     * @return True if the subcategory exists, otherwise false.
     */
    boolean existsByName(String name);

    /**
     * Find all subcategories by category ID.
     *
     * @param categoryId The ID of the category.
     * @return List of subcategories under the specified category.
     */
    List<SubCategory> findByCategoryId(Long categoryId);
}
