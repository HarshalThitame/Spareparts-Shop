package com.sparesparts.service;
import com.sparesparts.entity.BrandModel;

import java.util.List;
import java.util.Optional;

/**
 * Service for managing {@link BrandModel} entities.
 * Provides business logic related to brand models.
 */
public interface BrandModelService {

    /**
     * Create a new brand model.
     *
     * @param brandModel The brand model to create.
     * @return The created brand model.
     */
    BrandModel createBrandModel(BrandModel brandModel);

    /**
     * Retrieve a brand model by its ID.
     *
     * @param id The ID of the brand model.
     * @return An Optional containing the brand model if found, or empty if not.
     */
    Optional<BrandModel> getBrandModelById(Long id);

    /**
     * Retrieve all brand models.
     *
     * @return A list of all brand models.
     */
    List<BrandModel> getAllBrandModels();

    /**
     * Find brand models by name (case insensitive).
     *
     * @param name The name to search for.
     * @return A list of matching brand models.
     */
    List<BrandModel> findByName(String name);

    /**
     * Retrieve all brand models by brand ID.
     *
     * @param brandId The ID of the brand.
     * @return A list of brand models associated with the given brand.
     */
    List<BrandModel> getBrandModelsByBrandId(Long brandId);

    /**
     * Delete a brand model by its ID.
     *
     * @param id The ID of the brand model to delete.
     */
    void deleteBrandModelById(Long id);
}
