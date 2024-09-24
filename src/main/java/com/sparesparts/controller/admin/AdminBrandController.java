package com.sparesparts.controller.admin;

import com.sparesparts.entity.Brand;
import com.sparesparts.entity.BrandModel;
import com.sparesparts.repositories.BrandModelRepository;
import com.sparesparts.repositories.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/brand")
public class AdminBrandController {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private BrandModelRepository brandModelRepository;

    /**
     * Adds a new brand to the database.
     *
     * @param brand the brand to be added
     * @return ResponseEntity with the saved brand and HTTP status
     */
    @PostMapping()
    public ResponseEntity<?> addBrand(@RequestBody Brand brand) {
        try {
            // Set the brand reference for each brand model
            if (brand.getBrandModels() != null) {
                for (BrandModel model : brand.getBrandModels()) {
                    model.setBrand(brand); // Set the brand reference
                }
            }

            // Save the brand entity
            Brand savedBrand = brandRepository.save(brand);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedBrand);
        } catch (Exception e) {
            // Handle any unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while adding the brand: " + e.getMessage());
        }
    }


    /**
     * Adds a new model to an existing brand by brand ID.
     *
     * @param brandId the ID of the brand to associate the model with
     * @param model the model to be added
     * @return ResponseEntity with the saved model and HTTP status
     */
    @PostMapping("/{brandId}/models")
    public ResponseEntity<?> addModelToBrand(@PathVariable Long brandId, @RequestBody BrandModel model) {
        try {
            // Fetch the brand by ID
            Optional<Brand> brandOptional = brandRepository.findById(brandId);

            // Check if the brand exists
            if (brandOptional.isPresent()) {
                Brand brand = brandOptional.get();

                // Set the brand for the model and save it
                model.setBrand(brand);
                BrandModel savedModel = brandModelRepository.save(model);

                return ResponseEntity.status(HttpStatus.CREATED).body(savedModel);
            } else {
                // Return 404 if the brand is not found
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Brand not found");
            }
        } catch (Exception e) {
            // Handle unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while adding the model: " + e.getMessage());
        }
    }

    /**
     * Retrieves all brands.
     *
     * @return ResponseEntity with the list of brands and HTTP status
     */
    @GetMapping("/all")
    public ResponseEntity<?> getAllBrands() {
        try {
            // Fetch all brands from the repository
            List<Brand> brands = brandRepository.findAll();
            return ResponseEntity.ok(brands);
        } catch (Exception e) {
            // Handle unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the brands: " + e.getMessage());
        }
    }

    /**
     * Retrieves all models associated with a brand by brand ID.
     *
     * @param brandId the ID of the brand
     * @return ResponseEntity with the list of models and HTTP status
     */
    @GetMapping("/{brandId}/models")
    public ResponseEntity<?> getModelsByBrand(@PathVariable Long brandId) {
        try {
            // Fetch the brand by ID
            Optional<Brand> brandOptional = brandRepository.findById(brandId);

            if (brandOptional.isPresent()) {
                // Return the models associated with the brand
                List<BrandModel> models = brandOptional.get().getBrandModels();
                return ResponseEntity.ok(models);
            } else {
                // Return 404 if brand is not found
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Brand not found");
            }
        } catch (Exception e) {
            // Handle unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the models: " + e.getMessage());
        }
    }
    /**
     * Deletes a brand by its ID.
     *
     * @param brandId the ID of the brand to be deleted
     * @return ResponseEntity with HTTP status
     */
    @DeleteMapping("/{brandId}")
    public ResponseEntity<?> deleteBrand(@PathVariable Long brandId) {
        try {
            // Check if the brand exists
            if (brandRepository.existsById(brandId)) {
                // Delete the brand
                brandRepository.deleteById(brandId);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // Return 204 No Content
            } else {
                // Return 404 if the brand is not found
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Brand not found");
            }
        } catch (Exception e) {
            // Handle unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the brand: " + e.getMessage());
        }
    }
    /**
     * Updates an existing brand along with its models.
     *
     * @param brandId the ID of the brand to be updated
     * @param brand the updated brand object
     * @return ResponseEntity with the updated brand and HTTP status
     */
    @PutMapping("/{brandId}")
    public ResponseEntity<?> updateBrand(@PathVariable Long brandId, @RequestBody Brand brand) {
        try {
            // Fetch the existing brand
            Optional<Brand> existingBrandOptional = brandRepository.findById(brandId);

            // Check if the brand exists
            if (existingBrandOptional.isPresent()) {
                Brand existingBrand = existingBrandOptional.get();

                // Update the brand's properties
                existingBrand.setName(brand.getName()); // Assuming there is a name property

                // Update brand models
                if (brand.getBrandModels() != null) {
                    // Set the brand reference for each brand model
                    for (BrandModel model : brand.getBrandModels()) {
                        model.setBrand(existingBrand); // Set the brand reference
                    }
                    // Assuming you want to save the models as well
                    brandModelRepository.saveAll(brand.getBrandModels());
                }

                // Save the updated brand entity
                Brand updatedBrand = brandRepository.save(existingBrand);
                return ResponseEntity.ok(updatedBrand);
            } else {
                // Return 404 if the brand is not found
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Brand not found");
            }
        } catch (Exception e) {
            // Handle unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the brand: " + e.getMessage());
        }
    }
    /**
     * Deletes a brand model by its ID.
     *
     * @param modelId the ID of the brand model to be deleted
     * @return ResponseEntity with HTTP status
     */
    @DeleteMapping("/models/{modelId}")
    public ResponseEntity<?> deleteBrandModel(@PathVariable Long modelId) {
        try {
            // Check if the brand model exists
            if (brandModelRepository.existsById(modelId)) {
                // Delete the brand model
                brandModelRepository.deleteById(modelId);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // Return 204 No Content
            } else {
                // Return 404 if the brand model is not found
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Brand model not found");
            }
        } catch (Exception e) {
            // Handle unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the brand model: " + e.getMessage());
        }
    }


}
