package com.sparesparts.controller.customer;

import com.sparesparts.entity.Cart;
import com.sparesparts.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer/cart")
public class CusomerCartController {

    @Autowired
    private CartService cartService;

    /**
     * Adds or updates a cart for a customer.
     *
     * @param cart The Cart object that contains all the necessary details (user, items, total amount).
     * @return The updated cart after adding the product.
     */
    @PostMapping("/add")
    public ResponseEntity<Cart> addOrUpdateCart(@RequestBody Cart cart) {

        // Call the cart service to add or update the cart
        Cart updatedCart = cartService.addOrUpdateCart(cart);

        // Return the updated cart wrapped in a ResponseEntity
        return ResponseEntity.ok(updatedCart);
    }

    /**
     * Retrieves the cart for the given user.
     *
     * @param userId The ID of the user whose cart is to be retrieved.
     * @return The user's cart.
     */
    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCart(@PathVariable Long userId) {

        // Call the cart service to retrieve the user's cart by user ID
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
     * Clears all items in the user's cart.
     *
     * @param userId The ID of the user whose cart is to be cleared.
     * @return The empty cart after clearing all items.
     */
    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<Cart> clearCart(@PathVariable Long userId) {

        // Call the cart service to clear the user's cart
        Cart clearedCart = cartService.clearCart(userId);

        // Return the empty cart wrapped in a ResponseEntity
        return ResponseEntity.ok(clearedCart);
    }
}
