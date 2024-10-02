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
        return ResponseEntity.ok(products);
    }

}
