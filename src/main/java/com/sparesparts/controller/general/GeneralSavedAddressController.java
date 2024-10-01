package com.sparesparts.controller.general;

import com.sparesparts.entity.SavedAddress;
import com.sparesparts.service.SavedAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/general/saved-addresses")
public class GeneralSavedAddressController {
    private final SavedAddressService savedAddressService;

    @Autowired
    public GeneralSavedAddressController(SavedAddressService savedAddressService) {
        this.savedAddressService = savedAddressService;
    }

    @PostMapping
    public ResponseEntity<SavedAddress> createSavedAddress(@RequestBody SavedAddress savedAddress) {
        System.out.println(savedAddress);
        if (savedAddress.getId() != null) {
        return ResponseEntity.ok(savedAddressService.saveAddress(savedAddress));
        }
        else {
            savedAddress.setId(System.currentTimeMillis()-500000);
            return ResponseEntity.ok(savedAddressService.saveAddress(savedAddress));
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SavedAddress>> getAddressesByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(savedAddressService.getAddressesByUserId(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSavedAddress(@PathVariable Long id) {
        savedAddressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }
}
