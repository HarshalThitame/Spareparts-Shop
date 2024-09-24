package com.sparesparts.services;

import com.sparesparts.entity.Brand;

import java.util.List;
import java.util.Optional;

/**
 * Service for managing {@link Brand} entities.
 * Provides business logic related to brands.
 */
public interface BrandService {

    /**
     * Create a new brand.
     *
     * @param brand The brand to create.
     * @return The created brand.
     */
    Brand createBrand(Brand brand);

    /**
     * Retrieve a brand by its ID.
     *
     * @param id The ID of the brand.
     * @return An Optional containing the brand if found, or empty if not.
     */
    Optional<Brand> getBrandById(Long id);

    /**
     * Retrieve all brands.
     *
     * @return A list of all brands.
     */
    List<Brand> getAllBrands();

    /**
     * Find brands by name (case insensitive).
     *
     * @param name The name to search for.
     * @return A list of matching brands.
     */
    List<Brand> findByName(String name);

    /**
     * Delete a brand by its ID.
     *
     * @param id The ID of the brand to delete.
     */
    void deleteBrandById(Long id);
}
