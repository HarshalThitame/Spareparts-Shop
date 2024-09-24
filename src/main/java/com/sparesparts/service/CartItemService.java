package com.sparesparts.service;

import com.sparesparts.entity.CartItem;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing {@link CartItem} entities.
 */
public interface CartItemService {

    /**
     * Get cart items by the cart ID.
     *
     * @param cartId The ID of the cart.
     * @return A list of cart items.
     */
    List<CartItem> getCartItemsByCartId(Long cartId);

    /**
     * Save or update a cart item.
     *
     * @param cartItem The cart item to save.
     * @return The saved cart item.
     */
    CartItem saveCartItem(CartItem cartItem);

    /**
     * Delete a cart item by its ID.
     *
     * @param id The ID of the cart item.
     */
    void deleteCartItemById(Long id);
}

