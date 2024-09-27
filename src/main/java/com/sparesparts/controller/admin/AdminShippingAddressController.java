package com.sparesparts.controller.admin;


import com.sparesparts.entity.ShippingAddress;
import com.sparesparts.service.ShippingAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing shipping addresses by the admin.
 */
@RestController
@RequestMapping("/api/admin/shipping-addresses")
public class AdminShippingAddressController {

    private final ShippingAddressService shippingAddressService;

    @Autowired
    public AdminShippingAddressController(ShippingAddressService shippingAddressService) {
        this.shippingAddressService = shippingAddressService;
    }

    /**
     * Get a list of all shipping addresses.
     * @return List of all shipping addresses.
     */
    @GetMapping
    public ResponseEntity<List<ShippingAddress>> getAllShippingAddresses() {
        List<ShippingAddress> shippingAddresses = shippingAddressService.getAllShippingAddresses();
        return ResponseEntity.ok(shippingAddresses);
    }

    /**
     * Get a shipping address by its ID.
     * @param id The ID of the shipping address to retrieve.
     * @return The shipping address with the specified ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ShippingAddress> getShippingAddressById(@PathVariable Long id) {
        ShippingAddress shippingAddress = shippingAddressService.getShippingAddressById(id);
        return ResponseEntity.ok(shippingAddress);
    }

    /**
     * Update an existing shipping address.
     * @param id The ID of the shipping address to update.
     * @param shippingAddress The updated shipping address details.
     * @return The updated shipping address.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ShippingAddress> updateShippingAddress(@PathVariable Long id, @RequestBody ShippingAddress shippingAddress) {
        shippingAddress.setId(id); // Ensure the ID in the shipping address matches the path variable
        ShippingAddress updatedShippingAddress = shippingAddressService.updateShippingAddress(shippingAddress);
        return ResponseEntity.ok(updatedShippingAddress);
    }

    /**
     * Delete a shipping address by its ID.
     * @param id The ID of the shipping address to delete.
     * @return Response indicating the deletion result.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteShippingAddress(@PathVariable Long id) {
        shippingAddressService.deleteShippingAddress(id);
        return ResponseEntity.ok("Shipping address deleted successfully");
    }

    // Additional admin-specific methods (e.g., search by user) can be added here.
}
