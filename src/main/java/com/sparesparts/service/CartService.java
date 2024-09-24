package com.sparesparts.service;


import com.sparesparts.entity.Cart;

import java.util.Optional;

/**
 * Service interface for managing {@link Cart} entities.
 */
public interface CartService {

    /**
     * Get a cart by the user ID.
     *
     * @param userId The ID of the user.
     * @return The cart associated with the given user.
     */
    Optional<Cart> getCartByUserId(Long userId);

    /**
     * Save or update a cart.
     *
     * @param cart The cart to save.
     * @return The saved cart.
     */
    Cart saveCart(Cart cart);

    /**
     * Delete a cart by its ID.
     *
     * @param id The ID of the cart.
     */
    void deleteCartById(Long id);

    /**
     * Adds or updates a cart for a user.
     * If the cart already exists, updates the cart with new items or updated quantities.
     *
     * @param cart The cart to be added or updated.
     * @return The updated or newly created cart.
     */
    Cart addOrUpdateCart(Cart cart);

    /**
     * Removes a product from the user's cart.
     *
     * @param userId The ID of the user.
     * @param productId The ID of the product to be removed from the cart.
     * @return The updated cart after the product is removed.
     */
    Cart removeFromCart(Long userId, Long productId);

    /**
     * Clears all items from the user's cart.
     *
     * @param userId The ID of the user whose cart is to be cleared.
     * @return The cleared cart.
     */
    Cart clearCart(Long userId);}

