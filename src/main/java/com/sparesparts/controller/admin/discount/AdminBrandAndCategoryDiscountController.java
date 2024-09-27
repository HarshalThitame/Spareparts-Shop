package com.sparesparts.controller.admin.discount;

import com.sparesparts.entity.Product;
import com.sparesparts.entity.helper.BrandAndCategoryDiscountRequest;
import com.sparesparts.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admin/discounts/brand-category")
public class AdminBrandAndCategoryDiscountController {

    @Autowired
    private ProductService productService;

    // Apply all discounts (retailer, mechanic, customer)
    @PostMapping("/apply-all")
    public ResponseEntity<List<Product>> applyAllDiscounts(@RequestBody BrandAndCategoryDiscountRequest request) {
        List<Product> products = productService.searchByBrandAndCategory(request.getBrand(), request.getCategory());
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<Product> updatedProducts = new ArrayList<>();
        for (Product product : products) {
            product.setDiscountToRetailer(request.getRetailerDiscount());
            product.setDiscountToMechanics(request.getMechanicDiscount());
            product.setDiscountOnPurchase(request.getCustomerDiscount());
            Product savedProduct = productService.saveProduct(product);
            updatedProducts.add(savedProduct);
        }
        return ResponseEntity.ok(updatedProducts);
    }

    // Apply retailer and mechanic discounts (no customer discount)
    @PostMapping("/apply-retailer-mechanic")
    public ResponseEntity<List<Product>> applyRetailerAndMechanicDiscounts(@RequestBody BrandAndCategoryDiscountRequest request) {
        List<Product> products = productService.searchByBrandAndCategory(request.getBrand(), request.getCategory());
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<Product> updatedProducts = new ArrayList<>();
        for (Product product : products) {
            product.setDiscountToRetailer(request.getRetailerDiscount());
            product.setDiscountToMechanics(request.getMechanicDiscount());
            Product savedProduct = productService.saveProduct(product);
            updatedProducts.add(savedProduct);
        }
        return ResponseEntity.ok(updatedProducts);
    }

    // Apply retailer and customer discounts (no mechanic discount)
    @PostMapping("/apply-retailer-customer")
    public ResponseEntity<List<Product>> applyRetailerAndCustomerDiscounts(@RequestBody BrandAndCategoryDiscountRequest request) {
        List<Product> products = productService.searchByBrandAndCategory(request.getBrand(), request.getCategory());
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<Product> updatedProducts = new ArrayList<>();
        for (Product product : products) {
            product.setDiscountToRetailer(request.getRetailerDiscount());
            product.setDiscountOnPurchase(request.getCustomerDiscount());
            Product savedProduct = productService.saveProduct(product);
            updatedProducts.add(savedProduct);
        }
        return ResponseEntity.ok(updatedProducts);
    }

    // Apply mechanic and customer discounts (no retailer discount)
    @PostMapping("/apply-mechanic-customer")
    public ResponseEntity<List<Product>> applyMechanicAndCustomerDiscounts(@RequestBody BrandAndCategoryDiscountRequest request) {
        List<Product> products = productService.searchByBrandAndCategory(request.getBrand(), request.getCategory());
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<Product> updatedProducts = new ArrayList<>();
        for (Product product : products) {
            product.setDiscountToMechanics(request.getMechanicDiscount());
            product.setDiscountOnPurchase(request.getCustomerDiscount());
            Product savedProduct = productService.saveProduct(product);
            updatedProducts.add(savedProduct);
        }
        return ResponseEntity.ok(updatedProducts);
    }

    // Apply only retailer discount
    @PostMapping("/apply-retailer")
    public ResponseEntity<List<Product>> applyRetailerDiscount(@RequestBody BrandAndCategoryDiscountRequest request) {
        List<Product> products = productService.searchByBrandAndCategory(request.getBrand(), request.getCategory());
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

    // Apply only mechanic discount
    @PostMapping("/apply-mechanic")
    public ResponseEntity<List<Product>> applyMechanicDiscount(@RequestBody BrandAndCategoryDiscountRequest request) {
        List<Product> products = productService.searchByBrandAndCategory(request.getBrand(), request.getCategory());
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

    // Apply only customer discount
    @PostMapping("/apply-customer")
    public ResponseEntity<List<Product>> applyCustomerDiscount(@RequestBody BrandAndCategoryDiscountRequest request) {
        List<Product> products = productService.searchByBrandAndCategory(request.getBrand(), request.getCategory());
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
}
