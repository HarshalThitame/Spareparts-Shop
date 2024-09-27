package com.sparesparts.controller.machanic;

import com.sparesparts.entity.ShippingAddress;
import com.sparesparts.service.ShippingAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mechanic/shipping-addresses")
public class MechanicShippingAddressController {

    private final ShippingAddressService shippingAddressService;

    @Autowired
    public MechanicShippingAddressController(ShippingAddressService shippingAddressService) {
        this.shippingAddressService = shippingAddressService;
    }

    // Get all shipping addresses for the mechanic
    @GetMapping
    public ResponseEntity<List<ShippingAddress>> getAllShippingAddresses() {
        List<ShippingAddress> shippingAddresses = shippingAddressService.getAllShippingAddresses();
        return ResponseEntity.ok(shippingAddresses);
    }

    // Get shipping address by ID
    @GetMapping("/{id}")
    public ResponseEntity<ShippingAddress> getShippingAddressById(@PathVariable Long id) {
        ShippingAddress shippingAddress = shippingAddressService.getShippingAddressById(id);
        return ResponseEntity.ok(shippingAddress);
    }

    // Create a new shipping address
    @PostMapping
    public ResponseEntity<ShippingAddress> createShippingAddress(@RequestBody ShippingAddress shippingAddress) {
        ShippingAddress createdShippingAddress = shippingAddressService.createShippingAddress(shippingAddress);
        return new ResponseEntity<>(createdShippingAddress, HttpStatus.CREATED);
    }

    // Update an existing shipping address
    @PutMapping("/{id}")
    public ResponseEntity<ShippingAddress> updateShippingAddress(@PathVariable Long id, @RequestBody ShippingAddress shippingAddress) {
        shippingAddress.setId(id); // Ensure the ID is set for update
        ShippingAddress updatedShippingAddress = shippingAddressService.updateShippingAddress(shippingAddress);
        return ResponseEntity.ok(updatedShippingAddress);
    }

    // Delete a shipping address
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShippingAddress(@PathVariable Long id) {
        shippingAddressService.deleteShippingAddress(id);
        return ResponseEntity.noContent().build();
    }
}
