package com.sparesparts.controller.admin.discount;

import com.sparesparts.entity.Product;
import com.sparesparts.entity.helper.BrandAndCategoryDiscountRequest;
import com.sparesparts.entity.helper.BrandAndModelDiscountRequest;
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
@RequestMapping("/api/admin/discounts/brand-model")
public class AdminBrandAndModelDiscountController {

    @Autowired
    private ProductService productService;

    // Apply all discounts: retailer, mechanic, and customer
    @PostMapping("/apply-all-discounts")
    public ResponseEntity<List<Product>> applyAllDiscounts(@RequestBody BrandAndModelDiscountRequest discountRequest) {
        Long brandId = discountRequest.getBrand();
        Long modelId = discountRequest.getModel();

        List<Product> products = productService.searchByBrandAndModel(brandId, modelId);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        for (Product product : products) {
            product.setDiscountToRetailer(discountRequest.getRetailerDiscount());
            product.setDiscountToMechanics(discountRequest.getMechanicDiscount());
            product.setDiscountOnPurchase(discountRequest.getCustomerDiscount());
            productService.saveProduct(product); // Save updated product
        }

        return ResponseEntity.ok(products);
    }

    // Apply retailer and mechanic discounts when customer discount is not applied
    @PostMapping("/apply-retailer-and-mechanic-discounts")
    public ResponseEntity<List<Product>> applyRetailerAndMechanicDiscounts(@RequestBody BrandAndModelDiscountRequest discountRequest) {
        Long brandId = discountRequest.getBrand();
        Long modelId = discountRequest.getModel();

        List<Product> products = productService.searchByBrandAndModel(brandId, modelId);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        for (Product product : products) {
            product.setDiscountToRetailer(discountRequest.getRetailerDiscount());
            product.setDiscountToMechanics(discountRequest.getMechanicDiscount());
            productService.saveProduct(product);
        }

        return ResponseEntity.ok(products);
    }

    // Apply retailer and customer discounts when mechanic discount is not applied
    @PostMapping("/apply-retailer-and-customer-discounts")
    public ResponseEntity<List<Product>> applyRetailerAndCustomerDiscounts(@RequestBody BrandAndModelDiscountRequest discountRequest) {
        Long brandId = discountRequest.getBrand();
        Long modelId = discountRequest.getModel();

        List<Product> products = productService.searchByBrandAndModel(brandId, modelId);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        for (Product product : products) {
            product.setDiscountToRetailer(discountRequest.getRetailerDiscount());
            product.setDiscountOnPurchase(discountRequest.getCustomerDiscount());
            productService.saveProduct(product);
        }

        return ResponseEntity.ok(products);
    }

    // Apply mechanic and customer discounts when retailer discount is not applied
    @PostMapping("/apply-mechanic-and-customer-discounts")
    public ResponseEntity<List<Product>> applyMechanicAndCustomerDiscounts(@RequestBody BrandAndModelDiscountRequest discountRequest) {
        Long brandId = discountRequest.getBrand();
        Long modelId = discountRequest.getModel();

        List<Product> products = productService.searchByBrandAndModel(brandId, modelId);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        for (Product product : products) {
            product.setDiscountToMechanics(discountRequest.getMechanicDiscount());
            product.setDiscountOnPurchase(discountRequest.getCustomerDiscount());
            productService.saveProduct(product);
        }

        return ResponseEntity.ok(products);
    }

    // Apply only retailer discount
    @PostMapping("/apply-retailer-discount")
    public ResponseEntity<List<Product>> applyRetailerDiscount(@RequestBody BrandAndModelDiscountRequest discountRequest) {
        Long brandId = discountRequest.getBrand();
        Long modelId = discountRequest.getModel();

        List<Product> products = productService.searchByBrandAndModel(brandId, modelId);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        for (Product product : products) {
            product.setDiscountToRetailer(discountRequest.getRetailerDiscount());
            productService.saveProduct(product);
        }

        return ResponseEntity.ok(products);
    }

    // Apply only mechanic discount
    @PostMapping("/apply-mechanic-discount")
    public ResponseEntity<List<Product>> applyMechanicDiscount(@RequestBody BrandAndModelDiscountRequest discountRequest) {
        Long brandId = discountRequest.getBrand();
        Long modelId = discountRequest.getModel();

        List<Product> products = productService.searchByBrandAndModel(brandId, modelId);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        for (Product product : products) {
            product.setDiscountToMechanics(discountRequest.getMechanicDiscount());
            productService.saveProduct(product);
        }

        return ResponseEntity.ok(products);
    }

    // Apply only customer discount
    @PostMapping("/apply-customer-discount")
    public ResponseEntity<List<Product>> applyCustomerDiscount(@RequestBody BrandAndModelDiscountRequest discountRequest) {
        Long brandId = discountRequest.getBrand();
        Long modelId = discountRequest.getModel();

        List<Product> products = productService.searchByBrandAndModel(brandId, modelId);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        for (Product product : products) {
            product.setDiscountOnPurchase(discountRequest.getCustomerDiscount());
            productService.saveProduct(product);
        }

        return ResponseEntity.ok(products);
    }
}
