package com.sparesparts.service.serviceImpl;

import com.sparesparts.entity.*;
import com.sparesparts.repositories.*;
import com.sparesparts.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service implementation for managing {@link Product} entities.
 * Handles the business logic for products and communicates with the repository layer.
 */
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final BrandRepository brandRepository;
    private final BrandModelRepository brandModelRepository;

    /**
     * Constructor-based dependency injection for repositories.
     *
     * @param productRepository Repository for product data access.
     * @param categoryRepository Repository for category data access.
     */
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, SubCategoryRepository subCategoryRepository, BrandRepository brandRepository, BrandModelRepository brandModelRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.subCategoryRepository = subCategoryRepository;
        this.brandRepository = brandRepository;
        this.brandModelRepository = brandModelRepository;
    }

    /**
     * Retrieve all products.
     *
     * @return A list of all products.
     */
    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Retrieve a product by its ID.
     *
     * @param id The ID of the product.
     * @return An Optional containing the product if found, or empty otherwise.
     */
    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    /**
     * Save or update a product.
     *
     * @param product The product entity to be saved.
     * @return The saved product entity.
     */
    @Override
    @Transactional
    public Product saveProduct(Product product) {
        // Fetch and manage categories
        List<Category> existingCategories = categoryRepository.findAllById(
                product.getCategories().stream().map(Category::getId).collect(Collectors.toList()));
        product.setCategories(existingCategories);

        // Fetch and manage brands
        if (product.getBrands() != null) {
            List<Brand> existingBrands = brandRepository.findAllById(
                    product.getBrands().stream().map(Brand::getId).collect(Collectors.toList()));
            product.setBrands(existingBrands);
        }

        // Fetch and manage brand models
        List<BrandModel> existingBrandModels = brandModelRepository.findAllById(
                product.getBrandModels().stream().map(BrandModel::getId).collect(Collectors.toList()));
        product.setBrandModels(existingBrandModels);

        // Fetch and manage subcategories
        List<SubCategory> existingSubCategories = subCategoryRepository.findAllById(
                product.getSubCategories().stream().map(SubCategory::getId).collect(Collectors.toList()));
        product.setSubCategories(existingSubCategories);

        // Save the product with all managed entities
        return productRepository.save(product);
    }




    /**
     * Delete a product by its ID.
     *
     * @param id The ID of the product to be deleted.
     */
    @Override
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }



    /**
     * Search products by their name.
     *
     * @param name The name or part of the name of the product.
     * @return A list of products whose names contain the search string.
     */
    @Override
    public List<Product> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<Product> saveMultipleProducts(List<Product> products) {
        return productRepository.saveAll(products);
    }

    @Override
    public List<Product> getProductsBySubCategoryId(Long subCategoryId) {
        return productRepository.findBySubCategoriesId(subCategoryId);
    }
}

