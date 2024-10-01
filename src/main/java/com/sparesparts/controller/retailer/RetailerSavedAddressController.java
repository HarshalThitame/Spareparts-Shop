package com.sparesparts.controller.retailer;


import com.sparesparts.entity.SavedAddress;
import com.sparesparts.entity.User;
import com.sparesparts.service.SavedAddressService;
import com.sparesparts.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/retailer/{userId}/saved-addresses")
public class RetailerSavedAddressController {
    private final SavedAddressService savedAddressService;
    private final UserService userService;

    @Autowired
    public RetailerSavedAddressController(SavedAddressService savedAddressService, UserService userService) {
        this.savedAddressService = savedAddressService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<SavedAddress> createSavedAddress(@PathVariable Long userId, @RequestBody SavedAddress savedAddress) {
        Optional<User> user = userService.findById(userId);
        savedAddress.setUser(user.get());
        return ResponseEntity.ok(savedAddressService.saveAddress(savedAddress));
    }

    @GetMapping
    public ResponseEntity<List<SavedAddress>> getAddressesByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(savedAddressService.getAddressesByUserId(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSavedAddress(@PathVariable Long userId, @PathVariable Long id) {
        savedAddressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }
}
