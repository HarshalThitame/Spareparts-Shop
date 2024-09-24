package com.sparesparts.repositories;

import com.sparesparts.entity.BrandModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for managing {@link BrandModel} entities.
 * Provides methods for standard CRUD operations and custom queries for brand models.
 */
@Repository
public interface BrandModelRepository extends JpaRepository<BrandModel, Long> {

    /**
     * Find brand models by name containing a search string (case insensitive).
     *
     * @param name The search string for the brand model name.
     * @return A list of brand models whose name contains the given string.
     */
    List<BrandModel> findByNameContainingIgnoreCase(String name);

    /**
     * Find all brand models for a specific brand by its ID.
     *
     * @param brandId The ID of the brand.
     * @return A list of brand models associated with the given brand.
     */
    List<BrandModel> findByBrandId(Long brandId);
}
