package com.sparesparts.controller.admin;

import com.sparesparts.entity.Product;
import com.sparesparts.entity.helper.DiscountRequest;
import com.sparesparts.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/admin/discounts") // Updated path to include /api/admin/
public class AdminDiscountController {

    @Autowired
    private ProductService productService;

    @PostMapping("/apply-all")
    public ResponseEntity<Product> applyAllDiscounts(@RequestBody DiscountRequest request) {
        Long productId = request.getProduct();
        Optional<Product> product = productService.getProductById(productId);
        if (product.isPresent()) {
            Product newProduct = product.get();
            newProduct.setDiscountOnPurchase(request.getCustomerDiscount());
            newProduct.setDiscountToRetailer(request.getRetailerDiscount());
            newProduct.setDiscountToMechanics(request.getMechanicDiscount());
            productService.saveProduct(newProduct);
            return ResponseEntity.ok(newProduct);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/apply-retailer-mechanic")
    public ResponseEntity<Product> applyRetailerAndMechanicDiscounts(@RequestBody DiscountRequest request) {
        Long productId = request.getProduct();
        Optional<Product> product = productService.getProductById(productId);
        if (product.isPresent()) {
            Product newProduct = product.get();
            newProduct.setDiscountToRetailer(request.getRetailerDiscount());
            newProduct.setDiscountToMechanics(request.getMechanicDiscount());
            productService.saveProduct(newProduct);
            return ResponseEntity.ok(newProduct);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/apply-retailer-customer")
    public ResponseEntity<Product> applyRetailerAndCustomerDiscounts(@RequestBody DiscountRequest request) {
        Long productId = request.getProduct();
        Optional<Product> product = productService.getProductById(productId);
        if (product.isPresent()) {
            Product newProduct = product.get();
            newProduct.setDiscountOnPurchase(request.getCustomerDiscount());
            newProduct.setDiscountToRetailer(request.getRetailerDiscount());
            productService.saveProduct(newProduct);
            return ResponseEntity.ok(newProduct);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/apply-mechanic-customer")
    public ResponseEntity<Product> applyMechanicAndCustomerDiscounts(@RequestBody DiscountRequest request) {
        Long productId = request.getProduct();
        Optional<Product> product = productService.getProductById(productId);
        if (product.isPresent()) {
            Product newProduct = product.get();
            newProduct.setDiscountOnPurchase(request.getCustomerDiscount());
            newProduct.setDiscountToMechanics(request.getMechanicDiscount());
            productService.saveProduct(newProduct);
            return ResponseEntity.ok(newProduct);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/apply-retailer")
    public ResponseEntity<Product> applyRetailerDiscount(@RequestBody DiscountRequest request) {
        Long productId = request.getProduct();
        Optional<Product> product = productService.getProductById(productId);
        if (product.isPresent()) {
            Product newProduct = product.get();
            newProduct.setDiscountToRetailer(request.getRetailerDiscount());
            productService.saveProduct(newProduct);
            return ResponseEntity.ok(newProduct);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/apply-mechanic")
    public ResponseEntity<Product> applyMechanicDiscount(@RequestBody DiscountRequest request) {
        Long productId = request.getProduct();
        Optional<Product> product = productService.getProductById(productId);
        if (product.isPresent()) {
            Product newProduct = product.get();
            newProduct.setDiscountToMechanics(request.getMechanicDiscount());
            productService.saveProduct(newProduct);
            return ResponseEntity.ok(newProduct);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/apply-customer")
    public ResponseEntity<Product> applyCustomerDiscount(@RequestBody DiscountRequest request) {
        Long productId = request.getProduct();
        Optional<Product> product = productService.getProductById(productId);
        if (product.isPresent()) {
            Product newProduct = product.get();
            newProduct.setDiscountOnPurchase(request.getCustomerDiscount());
            productService.saveProduct(newProduct);
            return ResponseEntity.ok(newProduct);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/no-discounts")
    public ResponseEntity<Product> applyNoDiscounts(@RequestBody DiscountRequest request) {
        Long productId = request.getProduct();
        Optional<Product> product = productService.getProductById(productId);
        if (product.isPresent()) {
            Product newProduct = product.get();
            productService.saveProduct(newProduct);
            return ResponseEntity.ok(newProduct);
        }
        return ResponseEntity.notFound().build();
    }
}
