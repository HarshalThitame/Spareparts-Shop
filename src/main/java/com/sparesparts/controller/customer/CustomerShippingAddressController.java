package com.sparesparts.controller.customer;

import com.sparesparts.entity.ShippingAddress;
import com.sparesparts.service.ShippingAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing shipping addresses of the authenticated customer.
 */
@RestController
@RequestMapping("/api/customer/shipping-addresses")
public class CustomerShippingAddressController {

    private final ShippingAddressService shippingAddressService;

    @Autowired
    public CustomerShippingAddressController(ShippingAddressService shippingAddressService) {
        this.shippingAddressService = shippingAddressService;
    }

    /**
     * Get all shipping addresses for the authenticated customer.
     *
     * @return List of shipping addresses for the customer.
     */
    @GetMapping
    public ResponseEntity<List<ShippingAddress>> getAllShippingAddresses() {
        List<ShippingAddress> shippingAddresses = shippingAddressService.getAllShippingAddresses(); // Implement method to get addresses
        return ResponseEntity.ok(shippingAddresses);
    }

    /**
     * Get a shipping address by its ID for the authenticated customer.
     *
     * @param id The ID of the shipping address to retrieve.
     * @return The shipping address with the specified ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ShippingAddress> getShippingAddressById(@PathVariable Long id) {
        ShippingAddress shippingAddress = shippingAddressService.getShippingAddressById(id); // Implement method to retrieve a specific address
        return ResponseEntity.ok(shippingAddress);
    }

    /**
     * Get all shipping addresses for a specific user.
     *
     * @param userId The ID of the user whose addresses are to be retrieved.
     * @return List of shipping addresses for the user.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ShippingAddress>> getShippingAddressesByUserId(@PathVariable Long userId) {
        List<ShippingAddress> shippingAddresses = shippingAddressService.getShippingAddressesByUserId(userId);
        return ResponseEntity.ok(shippingAddresses);
    }

    /**
     * Add a new shipping address for the authenticated customer.
     *
     * @param shippingAddress The shipping address details to be added.
     * @return The added shipping address.
     */
    @PostMapping
    public ResponseEntity<ShippingAddress> addShippingAddress(@RequestBody ShippingAddress shippingAddress) {
        ShippingAddress addedShippingAddress = shippingAddressService.addShippingAddress(shippingAddress); // Implement method to add new address
        return ResponseEntity.ok(addedShippingAddress);
    }

    /**
     * Update an existing shipping address for the authenticated customer.
     *
     * @param id              The ID of the shipping address to update.
     * @param shippingAddress The updated shipping address details.
     * @return The updated shipping address.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ShippingAddress> updateShippingAddress(@PathVariable Long id, @RequestBody ShippingAddress shippingAddress) {
        shippingAddress.setId(id); // Ensure the ID in the shipping address matches the path variable
        ShippingAddress updatedShippingAddress = shippingAddressService.updateShippingAddress(shippingAddress); // Implement method to update address
        return ResponseEntity.ok(updatedShippingAddress);
    }

    /**
     * Delete a shipping address by its ID for the authenticated customer.
     *
     * @param id The ID of the shipping address to delete.
     * @return Response indicating the deletion result.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteShippingAddress(@PathVariable Long id) {
        shippingAddressService.deleteShippingAddress(id); // Implement method to delete address
        return ResponseEntity.ok("Shipping address deleted successfully");
    }
}
