package com.sparesparts.controller.admin.discount;

import com.sparesparts.entity.Product;
import com.sparesparts.entity.helper.CategoryDiscountRequest; // Request class for category discounts
import com.sparesparts.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admin/discounts/category") // Endpoint for discounts
public class AdminCategoryDiscountController {

    @Autowired
    private ProductService productService; // Service for managing products

    @PostMapping("/apply-all")
    public ResponseEntity<List<Product>> applyAllDiscounts(@RequestBody CategoryDiscountRequest discountRequest) {
        Long categoryId = discountRequest.getCategory();

        List<Product> products = productService.searchByCategory(categoryId);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<Product> filteredProducts = new ArrayList<>();
        for (Product product : products) {
            product.setDiscountToMechanics(discountRequest.getMechanicDiscount());
            product.setDiscountToRetailer(discountRequest.getRetailerDiscount());
            product.setDiscountOnPurchase(discountRequest.getCustomerDiscount());
            Product savedProduct = productService.saveProduct(product);
            filteredProducts.add(savedProduct);
        }
        return ResponseEntity.ok(filteredProducts);
    }

    @PostMapping("/apply-retailer-mechanic")
    public ResponseEntity<List<Product>> applyRetailerAndMechanicDiscounts(@RequestBody CategoryDiscountRequest discountRequest) {
        Long categoryId = discountRequest.getCategory();

        List<Product> products = productService.searchByCategory(categoryId);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<Product> filteredProducts = new ArrayList<>();
        for (Product product : products) {
            product.setDiscountToMechanics(discountRequest.getMechanicDiscount());
            product.setDiscountToRetailer(discountRequest.getRetailerDiscount());
            Product savedProduct = productService.saveProduct(product);
            filteredProducts.add(savedProduct);
        }
        return ResponseEntity.ok(filteredProducts);
    }

    @PostMapping("/apply-retailer-customer")
    public ResponseEntity<List<Product>> applyRetailerAndCustomerDiscounts(@RequestBody CategoryDiscountRequest discountRequest) {
        Long categoryId = discountRequest.getCategory();

        List<Product> products = productService.searchByCategory(categoryId);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<Product> filteredProducts = new ArrayList<>();
        for (Product product : products) {
            product.setDiscountToRetailer(discountRequest.getRetailerDiscount());
            product.setDiscountOnPurchase(discountRequest.getCustomerDiscount());
            Product savedProduct = productService.saveProduct(product);
            filteredProducts.add(savedProduct);
        }
        return ResponseEntity.ok(filteredProducts);
    }

    @PostMapping("/apply-mechanic-customer")
    public ResponseEntity<List<Product>> applyMechanicAndCustomerDiscounts(@RequestBody CategoryDiscountRequest discountRequest) {
        Long categoryId = discountRequest.getCategory();

        List<Product> products = productService.searchByCategory(categoryId);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<Product> filteredProducts = new ArrayList<>();
        for (Product product : products) {
            product.setDiscountToMechanics(discountRequest.getMechanicDiscount());
            product.setDiscountOnPurchase(discountRequest.getCustomerDiscount());
            Product savedProduct = productService.saveProduct(product);
            filteredProducts.add(savedProduct);
        }
        return ResponseEntity.ok(filteredProducts);
    }

    @PostMapping("/apply-retailer")
    public ResponseEntity<List<Product>> applyRetailerDiscount(@RequestBody CategoryDiscountRequest discountRequest) {
        Long categoryId = discountRequest.getCategory();

        List<Product> products = productService.searchByCategory(categoryId);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<Product> filteredProducts = new ArrayList<>();
        for (Product product : products) {
            product.setDiscountToRetailer(discountRequest.getRetailerDiscount());
            Product savedProduct = productService.saveProduct(product);
            filteredProducts.add(savedProduct);
        }
        return ResponseEntity.ok(filteredProducts);
    }

    @PostMapping("/apply-mechanic")
    public ResponseEntity<List<Product>> applyMechanicDiscount(@RequestBody CategoryDiscountRequest discountRequest) {
        Long categoryId = discountRequest.getCategory();

        List<Product> products = productService.searchByCategory(categoryId);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<Product> filteredProducts = new ArrayList<>();
        for (Product product : products) {
            product.setDiscountToMechanics(discountRequest.getMechanicDiscount());
            Product savedProduct = productService.saveProduct(product);
            filteredProducts.add(savedProduct);
        }
        return ResponseEntity.ok(filteredProducts);
    }

    @PostMapping("/apply-customer")
    public ResponseEntity<List<Product>> applyCustomerDiscount(@RequestBody CategoryDiscountRequest discountRequest) {
        Long categoryId = discountRequest.getCategory();

        List<Product> products = productService.searchByCategory(categoryId);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<Product> filteredProducts = new ArrayList<>();
        for (Product product : products) {
            product.setDiscountOnPurchase(discountRequest.getCustomerDiscount());
            Product savedProduct = productService.saveProduct(product);
            filteredProducts.add(savedProduct);
        }
        return ResponseEntity.ok(filteredProducts);
    }


}
