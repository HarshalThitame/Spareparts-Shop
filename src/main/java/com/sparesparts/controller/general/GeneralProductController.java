package com.sparesparts.controller.general;

import com.sparesparts.entity.Product;
import com.sparesparts.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for general actions that are accessible to all users.
 * No authentication is required for these endpoints.
 */
@RestController
@RequestMapping("/api/general/products")
public class GeneralProductController {

    private final ProductService productService;

    public GeneralProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword) {
        List<Product> products = productService.searchProductsByKeywords(keyword);
        List<Product> unblockedProducts = products.stream()
                .filter(product -> !product.isBlocked()) // Assuming the Product entity has an isBlocked method
                .toList();
        if (unblockedProducts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping("/top-selling-products")
    public ResponseEntity<List<Product>> getTopSellingProducts(@RequestParam(defaultValue = "6") int limit) {
        List<Product> products = productService.getTopSellingProducts(limit);
        List<Product> unblockedProducts = products.stream()
                .filter(product -> !product.isBlocked()) // Assuming the Product entity has an isBlocked method
                .toList();
        if (unblockedProducts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping("/top18")
    public ResponseEntity<List<Product>> getTop18Products() {
        List<Product> products = productService.getTop18Products();
        List<Product> unblockedProducts = products.stream()
                .filter(product -> !product.isBlocked()) // Assuming the Product entity has an isBlocked method
                .toList();
        if (unblockedProducts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(unblockedProducts);
    }
}
