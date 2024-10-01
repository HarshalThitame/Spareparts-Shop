package com.sparesparts.controller.admin.alert;

import com.sparesparts.entity.Order;
import com.sparesparts.entity.Product;
import com.sparesparts.service.OrderService;
import com.sparesparts.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/admin/alert")
public class AdminAlertController {
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;


    @GetMapping("/products/low-stock")
    public ResponseEntity<List<Product>> getLowStockProducts() {
        List<Product> lowStockProducts = productService.getLowStockProducts();// Example threshold
        if (lowStockProducts != null) {
            return ResponseEntity.ok(lowStockProducts);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/products/dead")
    public ResponseEntity<List<Product>> getDeadProducts() {
        List<Product> deadProducts = productService.getDeadProducts();// Custom query to find products that haven't sold in a long time
        if (deadProducts != null) {
            return ResponseEntity.ok(deadProducts);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/products/recent-updates")
    public ResponseEntity<List<Product>> getRecentlyUpdatedProducts() {
        List<Product> recentlyUpdatedProducts = productService.getRecentlyUpdatedProducts(30);// Example 30 days
        if (recentlyUpdatedProducts != null) {
            return ResponseEntity.ok(recentlyUpdatedProducts);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/orders/updates")
    public ResponseEntity<List<Order>> getOrderUpdates() {
        List<Order> orderUpdates = orderService.getOrderUpdates();// Custom logic for recent order updates
        if (orderUpdates != null) {
            return ResponseEntity.ok(orderUpdates);
        }
        return ResponseEntity.notFound().build();
    }



}
