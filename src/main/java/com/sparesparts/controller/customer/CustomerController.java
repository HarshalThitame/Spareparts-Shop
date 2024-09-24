package com.sparesparts.controller.customer;

import com.sparesparts.customeexception.ResourceNotFoundException;
import com.sparesparts.entity.Cart;
import com.sparesparts.entity.Wishlist;
import com.sparesparts.service.CartService;
import com.sparesparts.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for handling customer-specific actions.
 * Only users with the CUSTOMER role can access these endpoints.
 */
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final CartService cartService;
    private final WishlistService wishlistService;

    @Autowired
    public CustomerController(CartService cartService, WishlistService wishlistService) {
        this.cartService = cartService;
        this.wishlistService = wishlistService;
    }



    /**
     * Add an item to the customer's wishlist.
     *
     * @param userId The ID of the customer.
     * @param wishlist The wishlist item to be added.
     * @return The updated wishlist.
     */
    @PostMapping("/wishlist/{userId}")
    public ResponseEntity<Wishlist> addToWishlist(@PathVariable Long userId, @RequestBody Wishlist wishlist) {
        wishlist.getUser().setId(userId);
        return ResponseEntity.ok(wishlistService.saveWishlist(wishlist));
    }

    /**
     * Remove an item from the wishlist.
     *
     * @param id The ID of the wishlist item to remove.
     * @return Confirmation message.
     */
    @DeleteMapping("/wishlist/{id}")
    public ResponseEntity<String> deleteWishlistItem(@PathVariable Long id) {
        wishlistService.deleteWishlistById(id);
        return ResponseEntity.ok("Wishlist item removed successfully.");
    }
}

