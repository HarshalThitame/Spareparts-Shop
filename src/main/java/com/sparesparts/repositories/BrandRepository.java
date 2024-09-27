package com.sparesparts.repositories;

import com.sparesparts.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for managing {@link Brand} entities.
 * Provides methods for standard CRUD operations and custom queries for brands.
 */
@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    /**
     * Find brands by name containing a search string (case insensitive).
     *
     * @param name The search string for the brand name.
     * @return A list of brands whose name contains the given string.
     */
    List<Brand> findByNameContainingIgnoreCase(String name);

    Brand findByName(String trim);
}
