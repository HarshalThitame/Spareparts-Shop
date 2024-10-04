package com.sparesparts.controller.admin;

import com.sparesparts.config.aws.S3Service;
import com.sparesparts.dto.ImagesDTO;
import com.sparesparts.entity.Images;
import com.sparesparts.entity.Product;
import com.sparesparts.service.ImagesService;
import com.sparesparts.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for handling product-related actions.
 * Accessible only to users with the ADMIN role.
 */
@RestController
@RequestMapping("/api/admin/products")
public class ProductController {

    private final ProductService productService;
    private final S3Service s3Service;
    private final ImagesService imagesService;


    @Autowired
    public ProductController(ProductService productService, S3Service s3Service, ImagesService imagesService) {
        this.productService = productService;
        this.s3Service = s3Service;
        this.imagesService = imagesService;
    }

    /**
     * Add a new product.
     *
     * @param product The product to be added.
     * @return The added product.
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product savedProduct = productService.saveProduct(product);
        return ResponseEntity.ok(savedProduct);
    }

    @PostMapping("/update-stock")
    public ResponseEntity<Product> updateStock(@RequestBody Product product){
        Optional<Product> productById = productService.getProductById(product.getId());
        productById.get().setStockQuantity(product.getStockQuantity());
        Product saveProduct = productService.saveProduct(productById.get());
        return ResponseEntity.ok(saveProduct);
    }

    @PostMapping("/blocked")
    public ResponseEntity<Product> updateIsBlocked(@RequestBody Product product){
        Optional<Product> productById = productService.getProductById(product.getId());
        productById.get().setBlocked(product.isBlocked());
        Product saveProduct = productService.saveProduct(productById.get());
        return ResponseEntity.ok(saveProduct);
    }

    /**
     * Add multiple products.
     *
     * @param products The list of products to be added.
     * @return The list of added products.
     */
    @PostMapping("/multiple")
    public ResponseEntity<List<Product>> addMultipleProducts(@RequestBody List<Product> products) {
        List<Product> newProducts = new ArrayList<>();
        for (Product product : products) {
            Product saveProduct = productService.saveProduct(product);
            newProducts.add(saveProduct);
        }
        return ResponseEntity.ok(newProducts);
    }


    /**
     * Get all products.
     *
     * @return A list of all products.
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/by-pagination")
    public Page<Product> getPaginatedProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return productService.getPaginatedProducts(page, size);
    }

    /**
     * Get a product by its ID.
     *
     * @param id The ID of the product.
     * @return The product with the given ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping("/upload")
    public ResponseEntity<?> uploadProducts(@RequestParam("file") MultipartFile file) {

        try {
            Map<String, Object> response = productService.saveProductsFromCSV(file);
            if (response.isEmpty()) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Products uploaded successfully!");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Some products could not be added: " + response);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing file: " + e.getMessage());
        }
    }

    @PostMapping("/upload-main-image/{id}")
    public ResponseEntity<Product> uploadProductWithImage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile image) {


        Product product = productService.getProductById(id).get();
        try {
            // Upload the image to S3
            String imageUrl = s3Service.uploadFile(image); // Your upload method

            product.setMainImage(imageUrl);
            // Here you can save the product details to your database
            // (e.g., product.setImageUrl(imageUrl); productRepository.save(product);)

            productService.saveProduct(product);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.ok(new Product());
        }
    }

    @PostMapping("/upload-cover-image")
    public ResponseEntity<Images> uploadProductWithCoverImage(@RequestBody ImagesDTO imagesDTO) {
        System.out.println(imagesDTO);
        if (imagesDTO == null || imagesDTO.getProductId() == null) {
            return ResponseEntity.badRequest().body(null); // Return bad request if product id is missing
        }

        // Fetch the existing Product from the database
        Optional<Product> optionalProduct = productService.getProductById(imagesDTO.getProductId());
        if (!optionalProduct.isPresent()) {
            return ResponseEntity.notFound().build(); // Return not found if the product does not exist
        }
        Product product = optionalProduct.get();

        // Create an Images object
        Images images = new Images();
        images.setUrl(imagesDTO.getUrl());
        images.setProduct(product);

        Images uploadedImages = imagesService.uploadImages(images);
        if (uploadedImages == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Return error if upload fails
        } else {
            return ResponseEntity.ok(uploadedImages); // Return the uploaded images
        }
    }




}
