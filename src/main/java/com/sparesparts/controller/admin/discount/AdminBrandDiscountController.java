package com.sparesparts.controller.admin.discount;


import com.sparesparts.entity.Product;
import com.sparesparts.entity.helper.BrandDiscountRequest;
import com.sparesparts.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admin/discounts/brand")
public class AdminBrandDiscountController {


    @Autowired
    private ProductService productService;


    @PostMapping("/apply-all")
    public ResponseEntity<List<Product>> applyAllDiscounts(@RequestBody BrandDiscountRequest request) {
        Long brandId = request.getBrand();
        List<Product> products = productService.searchByBrand(brandId);

        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<Product> updatedProducts = new ArrayList<>();
        for (Product product : products) {
            product.setDiscountToMechanics(request.getMechanicDiscount());
            product.setDiscountToRetailer(request.getRetailerDiscount());
            product.setDiscountOnPurchase(request.getCustomerDiscount());
            Product savedProduct = productService.saveProduct(product);
            updatedProducts.add(savedProduct);
        }
        return ResponseEntity.ok(updatedProducts);
    }

    @PostMapping("/apply-retailer-mechanic")
    public ResponseEntity<List<Product>> applyRetailerAndMechanicDiscounts(@RequestBody BrandDiscountRequest request) {
        Long brandId = request.getBrand();
        List<Product> products = productService.searchByBrand(brandId);

        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<Product> updatedProducts = new ArrayList<>();
        for (Product product : products) {
            product.setDiscountToMechanics(request.getMechanicDiscount());
            product.setDiscountToRetailer(request.getRetailerDiscount());
            Product savedProduct = productService.saveProduct(product);
            updatedProducts.add(savedProduct);
        }
        return ResponseEntity.ok(updatedProducts);
    }

    @PostMapping("/apply-retailer-customer")
    public ResponseEntity<List<Product>> applyRetailerAndCustomerDiscounts(@RequestBody BrandDiscountRequest request) {
        Long brandId = request.getBrand();
        List<Product> products = productService.searchByBrand(brandId);

        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<Product> updatedProducts = new ArrayList<>();
        for (Product product : products) {
            product.setDiscountOnPurchase(request.getCustomerDiscount());
            product.setDiscountToRetailer(request.getRetailerDiscount());
            Product savedProduct = productService.saveProduct(product);
            updatedProducts.add(savedProduct);
        }
        return ResponseEntity.ok(updatedProducts);
    }

    @PostMapping("/apply-mechanic-customer")
    public ResponseEntity<List<Product>> applyMechanicAndCustomerDiscounts(@RequestBody BrandDiscountRequest request) {
        Long brandId = request.getBrand();
        List<Product> products = productService.searchByBrand(brandId);

        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<Product> updatedProducts = new ArrayList<>();
        for (Product product : products) {
            product.setDiscountOnPurchase(request.getCustomerDiscount());
            product.setDiscountToMechanics(request.getMechanicDiscount());
            Product savedProduct = productService.saveProduct(product);
            updatedProducts.add(savedProduct);
        }
        return ResponseEntity.ok(updatedProducts);
    }

    @PostMapping("/apply-retailer")
    public ResponseEntity<List<Product>> applyRetailerDiscount(@RequestBody BrandDiscountRequest request) {
        Long brandId = request.getBrand();
        List<Product> products = productService.searchByBrand(brandId);

        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<Product> updatedProducts = new ArrayList<>();
        for (Product product : products) {
            product.setDiscountToRetailer(request.getRetailerDiscount());
            Product savedProduct = productService.saveProduct(product);
            updatedProducts.add(savedProduct);
        }
        return ResponseEntity.ok(updatedProducts);
    }

    @PostMapping("/apply-mechanic")
    public ResponseEntity<List<Product>> applyMechanicDiscount(@RequestBody BrandDiscountRequest request) {
        Long brandId = request.getBrand();
        List<Product> products = productService.searchByBrand(brandId);

        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<Product> updatedProducts = new ArrayList<>();
        for (Product product : products) {
            product.setDiscountToMechanics(request.getMechanicDiscount());
            Product savedProduct = productService.saveProduct(product);
            updatedProducts.add(savedProduct);
        }
        return ResponseEntity.ok(updatedProducts);
    }

    @PostMapping("/apply-customer")
    public ResponseEntity<List<Product>> applyCustomerDiscount(@RequestBody BrandDiscountRequest request) {
        Long brandId = request.getBrand();
        List<Product> products = productService.searchByBrand(brandId);

        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<Product> updatedProducts = new ArrayList<>();
        for (Product product : products) {
            product.setDiscountOnPurchase(request.getCustomerDiscount());
            Product savedProduct = productService.saveProduct(product);
            updatedProducts.add(savedProduct);
        }
        return ResponseEntity.ok(updatedProducts);
    }

    @PostMapping("/no-discounts")
    public ResponseEntity<String> applyNoDiscounts(@RequestBody BrandDiscountRequest request) {
        // Logic when no discounts are applied (if needed)
        return ResponseEntity.ok("No discounts applied to brand: " + request.getBrand());
    }
}
