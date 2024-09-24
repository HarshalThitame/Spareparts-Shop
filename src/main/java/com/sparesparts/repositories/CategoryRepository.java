package com.sparesparts.repositories;

import com.sparesparts.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for managing {@link Category} entities.
 * Provides methods to manage product categories.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Find a category by its name.
     *
     * @param name The name of the category.
     * @return The category with the specified name.
     */
    Optional<Category> findByName(String name);

    /**
     * Check if a category exists by its name.
     *
     * @param name The name of the category.
     * @return True if the category exists, otherwise false.
     */
    boolean existsByName(String name);
}

