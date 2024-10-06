package com.sparesparts.service.serviceImpl;

import com.sparesparts.entity.*;
import com.sparesparts.repositories.*;
import com.sparesparts.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.*;
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
    private final ImagesRepository imagesRepository;
    private final OrderItemRepository orderItemRepository;

    /**
     * Constructor-based dependency injection for repositories.
     *
     * @param productRepository Repository for product data access.
     * @param categoryRepository Repository for category data access.
     */
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, SubCategoryRepository subCategoryRepository, BrandRepository brandRepository, BrandModelRepository brandModelRepository, ImagesRepository imagesRepository, OrderItemRepository orderItemRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.subCategoryRepository = subCategoryRepository;
        this.brandRepository = brandRepository;
        this.brandModelRepository = brandModelRepository;
        this.imagesRepository = imagesRepository;
        this.orderItemRepository = orderItemRepository;
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
        Optional<Product> product = productRepository.findById(id);
        List<Images> images = imagesRepository.findByProductId(product.get().getId());
        product.get().setImages(images);
        return product;
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

        Optional<Product> productById = productRepository.findById(product.getId());
        productById.ifPresent(value -> product.setCreatedAt(value.getCreatedAt()));

        if (productById.isEmpty()){
            product.setCreatedAt(LocalDateTime.now());
        }

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


    @Override
    public List<Product> searchByBrandsAndBrandModelAndCategory(Long brandId, Long brandModelId, Long categoryId) {
        // Call the repository method to find products by brand, model, and category
        return productRepository.findByBrandIdBrandModelIdCategoryId(brandId, brandModelId, categoryId);
    }

    @Override
    public List<Product> searchByBrandAndModel(Long brandId, Long brandModelId) {
        // Call the repository method to find products by brand and model
        return productRepository.findByBrandIdAndBrandModelId(brandId, brandModelId);
    }

    @Override
    public List<Product> searchByBrandAndCategory(Long brandId, Long categoryId) {
        // Call the repository method to find products by brand and category
        return productRepository.findByBrandIdAndCategoryId(brandId, categoryId);
    }

    @Override
    public List<Product> searchByModelAndCategory(Long brandModelId, Long categoryId) {
        // Call the repository method to find products by brand model and category
        return productRepository.findByBrandModelIdAndCategoryId(brandModelId, categoryId);
    }

    @Override
    public List<Product> searchByBrand(Long brandId) {
        // Call the repository method to find products by brand
        return productRepository.findByBrandId(brandId);
    }

    @Override
    public List<Product> searchByModel(Long brandModelId) {
        // Call the repository method to find products by brand model
        return productRepository.findByBrandModelId(brandModelId);
    }

    @Override
    public List<Product> searchByCategory(Long categoryId) {
        // Call the repository method to find products by category
        return productRepository.findByCategoryId(categoryId);
    }

    @Override
    public Map<String, Object> saveProductsFromCSV(MultipartFile file) {
        int successCount = 0;
        int errorCount = 0;
        List<Product> failedProducts = new ArrayList<>();
        List<String> errorMessages = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            boolean isFirstLine = true; // Flag to skip header
            while ((line = br.readLine()) != null) {


                String[] lineData = line.split(","); // Adjust as necessary
                Product product = null;
                try {
                    product = new Product();
                    product.setName(lineData[0]);
                    product.setDescription(lineData[1]);
                    product.setPrice(Double.parseDouble(lineData[2]));
                    product.setPartNumber(lineData[3]);
                    product.setMoq(Integer.parseInt(lineData[4]));
                    product.setStockQuantity(Integer.parseInt(lineData[5]));
                    product.setWeight(Double.parseDouble(lineData[6]));
                    product.setDimensions(lineData[7]);
                    product.setMaterial(lineData[8]);
                    product.setMainImage(lineData[9]);
                    product.setPublishedForCustomer(Boolean.parseBoolean(lineData[10]));


                    // Process Brands
                    List<Brand> brands = new ArrayList<>();
                    for (String brandName : lineData[11].split(";")) {
                        Brand brand = brandRepository.findByName(brandName.trim());
//                        if (brand == null) {
//                            brand = new Brand();
//                            brand.setName(brandName.trim());
//                            brand = brandRepository.save(brand);
//                        }
                        brands.add(brand);
                    }
                    product.setBrands(brands);

                    // Process Brand Models
                    List<BrandModel> brandModels = new ArrayList<>();
                    for (String brandModelName : lineData[12].split(";")) {
                        BrandModel brandModel = brandModelRepository.findByName(brandModelName.trim());
//                        if (brandModel == null) {
//                            brandModel = new BrandModel();
//                            brandModel.setName(brandModelName.trim());
//                            brandModel = brandModelRepository.save(brandModel);
//                        }
                        brandModels.add(brandModel);
                    }
                    product.setBrandModels(brandModels);

                    // Process Categories
                    List<Category> categories = new ArrayList<>();
                    for (String categoryName : lineData[13].split(";")) {
                        Category category = categoryRepository.findByName(categoryName.trim()).orElse(null);
//                        if (category == null) {
//                            category = new Category();
//                            category.setName(categoryName.trim());
//                            category = categoryRepository.save(category);
//                        }
                        categories.add(category);
                    }
                    product.setCategories(categories);

                    // Process SubCategories
                    List<SubCategory> subCategories = new ArrayList<>();
                    for (String subCategoryName : lineData[14].split(";")) {
                        SubCategory subCategory = subCategoryRepository.findByName(subCategoryName.trim()).orElse(null);
//                        if (subCategory == null) {
//                            subCategory = new SubCategory();
//                            subCategory.setName(subCategoryName.trim());
//                            subCategory = subCategoryRepository.save(subCategory);
//                        }
                        subCategories.add(subCategory);
                    }
                    product.setSubCategories(subCategories);

                    // Save product
                    productRepository.save(product);
                    successCount++;
                } catch (Exception e) {
                    errorCount++;
                    errorMessages.add("Error processing product: " + lineData[0] + " - " + e.getMessage());
                    failedProducts.add(product); // Add the product that failed to save
                }
            }
        } catch (IOException e) {
            errorMessages.add("Error reading CSV file: " + e.getMessage());
        }

        // Return the results
        Map<String, Object> result = new HashMap<>();
        result.put("failedProducts", failedProducts);
        result.put("successCount", successCount);
        result.put("errorCount", errorCount);
        result.put("errorMessages", errorMessages);
        return result;
    }



    private Map<String, Object> createSuccessResponse(int successCount, int errorCount, List<Product> failedProducts) {
        Map<String, Object> response = new HashMap<>();
        response.put("successCount", successCount);
        response.put("errorCount", errorCount);
        response.put("failedProducts", failedProducts); // Add failed products to response
        return response;
    }

    private Map<String, Object> createErrorResponse(int successCount, int errorCount, List<String> errorMessages, List<Product> failedProducts) {
        Map<String, Object> response = new HashMap<>();
        response.put("successCount", successCount);
        response.put("errorCount", errorCount);
        response.put("errorMessages", errorMessages);
        response.put("failedProducts", failedProducts); // Add failed products to response
        return response;
    }

    @Override
    public List<Product> getLowStockProducts() {
        List<Product> products = productRepository.findByStockQuantityLessThan(10);// Replace 10 with your threshold
//        products.sort(Comparator.comparingInt(Product::getStockQuantity));

        return products;
    }

    @Override
    public List<Product> getDeadProducts() {
        return productRepository.findDeadProducts(); // Assuming you have this method in your repository
    }

    @Override
    public List<Product> getRecentlyUpdatedProducts(int days) {
        // Define sorting by updatedAt in descending order
        Sort sort = Sort.by(Sort.Direction.DESC, "updatedAt");

        // Fetch products updated within the last 'days' days, sorted by updatedAt
        return productRepository.findByUpdatedAtAfter(LocalDateTime.now().minusDays(days), sort);
    }

    @Override
    public List<Product> searchProductsByKeywords(String keyword) {
        return productRepository.searchByKeyword(keyword);
    }

    @Override
    public List<Product> getTopSellingProducts(int limit) {
        List<Object[]> results = orderItemRepository.findTopSellingProducts();
        List<Product> topSellingProducts = new ArrayList<>();

        for (int i = 0; i < Math.min(limit, results.size()); i++) {
            Object[] result = results.get(i);
            Product product = (Product) result[0];
            topSellingProducts.add(product);
        }

        return topSellingProducts;
    }

    @Override
    public List<Product> getTop18Products() {
        return productRepository.findTop18ByOrderByIdAsc();
    }

    @Override
    public Page<Product> getPaginatedProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public void deleteImageFromProduct(Long productId, Long imageId) {
        // Find the product
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Find the image
        Images image = imagesRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found"));

        // Remove the image from the product's image list
        product.getImages().remove(image);

        // Optionally delete the image entity from the database
        imagesRepository.delete(image);

        // Save the updated product
        productRepository.save(product);
    }
}

