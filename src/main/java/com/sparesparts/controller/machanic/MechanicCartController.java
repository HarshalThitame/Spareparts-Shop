package com.sparesparts.controller.machanic;

import com.sparesparts.entity.Cart;
import com.sparesparts.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mechanic/cart")
public class MechanicCartController {

    @Autowired
    private CartService cartService;

    /**
     * Adds a product to the mechanic's cart or updates the existing cart.
     * If the cart already exists, the product is added, and the total amount is updated.
     * If no cart exists for the mechanic, a new cart is created and linked to the mechanic.
     *
     * @param cart The Cart object containing mechanic's details, list of cart items, and the total amount.
     * @return The updated Cart object after adding the product or updating the cart.
     */
    @PostMapping("/add")
    public ResponseEntity<Cart> addOrUpdateCart(@RequestBody Cart cart) {

        // Use the cartService to add or update the mechanic's cart
        Cart updatedCart = cartService.addOrUpdateCart(cart);

        // Return the updated cart as an HTTP response
        return ResponseEntity.ok(updatedCart);
    }

    /**
     * Retrieves the cart associated with the given mechanic user.
     * This method fetches the cart by the mechanic's user ID, returning all items and details in the cart.
     *
     * @param userId The ID of the mechanic whose cart is being retrieved.
     * @return The Cart object associated with the mechanic user.
     */
    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCart(@PathVariable Long userId) {

        // Fetch the mechanic's cart by user ID from the cartService
        Cart cart = cartService.getCartByUserId(userId).get();

        // Return the cart wrapped in a ResponseEntity
        return ResponseEntity.ok(cart);
    }

    /**
     * Removes a specific product from the mechanic's cart.
     * This method locates the mechanic's cart and removes the product with the given product ID.
     * After removal, the cart's total amount is recalculated.
     *
     * @param userId    The ID of the mechanic whose cart is being updated.
     * @param productId The ID of the product to be removed from the cart.
     * @return The updated Cart object after the product is removed.
     */
    @DeleteMapping("/remove")
    public ResponseEntity<Cart> removeFromCart(
            @RequestParam Long userId,
            @RequestParam Long productId) {

        // Remove the product from the mechanic's cart using the cartService
        Cart updatedCart = cartService.removeFromCart(userId, productId);

        // Return the updated cart wrapped in a ResponseEntity
        return ResponseEntity.ok(updatedCart);
    }

    /**
     * Clears all items from the mechanic's cart.
     * This method completely empties the mechanic's cart, removing all products and setting the total amount to zero.
     *
     * @param userId The ID of the mechanic whose cart is being cleared.
     * @return The empty Cart object after clearing all items.
     */
    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<Cart> clearCart(@PathVariable Long userId) {

        // Use the cartService to clear the mechanic's cart
        Cart clearedCart = cartService.clearCart(userId);

        // Return the cleared cart wrapped in a ResponseEntity
        return ResponseEntity.ok(clearedCart);
    }
}
