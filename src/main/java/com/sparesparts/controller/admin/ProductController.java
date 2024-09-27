package com.sparesparts.controller.admin;

import com.sparesparts.entity.Product;
import com.sparesparts.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
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


}
