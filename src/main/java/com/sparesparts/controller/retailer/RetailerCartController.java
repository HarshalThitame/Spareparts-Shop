package com.sparesparts.controller.retailer;

import com.sparesparts.entity.Cart;
import com.sparesparts.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/retailer/cart")
public class RetailerCartController {

    @Autowired
    private CartService cartService;

    /**
     * Adds a product to the user's cart or updates the existing cart.
     * If the cart already exists, the product is added to the cart, and the total amount is updated.
     * If no cart exists for the user, a new cart is created and associated with the user.
     *
     * @param cart The Cart object that contains user information, list of cart items, and the total amount.
     * @return The updated Cart object after adding the new product or updating the cart.
     */
    @PostMapping("/add")
    public ResponseEntity<Cart> addOrUpdateCart(@RequestBody Cart cart) {

        // Use the cartService to add or update the cart
        Cart updatedCart = cartService.addOrUpdateCart(cart);

        // Return the updated cart as an HTTP response
        return ResponseEntity.ok(updatedCart);
    }

    /**
     * Retrieves the cart associated with the specified user.
     * This method fetches the cart by the user's ID, returning all items and details in the cart.
     *
     * @param userId The ID of the user whose cart is being retrieved.
     * @return The Cart object associated with the user.
     */
    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCart(@PathVariable Long userId) {

        // Fetch the cart by user ID from the cartService
        Cart cart = cartService.getCartByUserId(userId).get();

        // Return the cart wrapped in a ResponseEntity
        return ResponseEntity.ok(cart);
    }

    /**
     * Removes a specific product from the user's cart.
     *
     * @param userId    The ID of the user whose cart is being updated.
     * @param productId The ID of the product to be removed from the cart.
     * @return The updated cart after removing the product.
     */
    @DeleteMapping("/remove/{userId}/{productId}")
    public ResponseEntity<Cart> removeFromCart(
            @PathVariable Long userId,
            @PathVariable Long productId) {

        // Call the cart service to remove the product from the user's cart
        Cart updatedCart = cartService.removeFromCart(userId, productId);

        // Return the updated cart wrapped in a ResponseEntity
        return ResponseEntity.ok(updatedCart);
    }

    /**
     * Clears all items from the user's cart.
     * This method completely empties the user's cart, removing all products and setting the total amount to zero.
     *
     * @param userId The ID of the user whose cart is being cleared.
     * @return The empty Cart object after clearing all items.
     */
    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<Cart> clearCart(@PathVariable Long userId) {

        // Use the cartService to clear the user's cart
        Cart clearedCart = cartService.clearCart(userId);

        // Return the cleared cart wrapped in a ResponseEntity
        return ResponseEntity.ok(clearedCart);
    }
}
