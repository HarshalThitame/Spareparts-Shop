package com.sparesparts.service.serviceImpl;

import com.sparesparts.entity.Cart;
import com.sparesparts.entity.CartItem;
import com.sparesparts.entity.User;
import com.sparesparts.repositories.CartRepository;
import com.sparesparts.repositories.UserRepository;
import com.sparesparts.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@link CartService}.
 */
@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<Cart> getCartByUserId(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.flatMap(cartRepository::findByUser);
    }

    @Override
    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public void deleteCartById(Long id) {
        cartRepository.deleteById(id);
    }


    /**
     * Adds or updates a cart for a user.
     * If the cart already exists, updates the cart with new items or updated quantities.
     *
     * @param cart The cart to be added or updated.
     * @return The updated or newly created cart.
     */
    @Override
    public Cart addOrUpdateCart(Cart cart) {
        // Retrieve the user associated with the cart
        User user = cart.getUser();

        // Retrieve the existing cart for the user, if available
        Optional<Cart> existingCartOpt = cartRepository.findByUserId(user.getId());

        if (existingCartOpt.isPresent()) {
            // If the cart exists, update the cart items
            Cart existingCart = existingCartOpt.get();
            updateCartItems(existingCart, cart.getItems());

            // Ensure all cart items are properly associated with the cart
            for (CartItem item : existingCart.getItems()) {
                item.setCart(existingCart);
            }

            // Recalculate the total amount
            existingCart.setTotalAmount(calculateTotalAmount(existingCart.getItems()));

            // Save and return the updated cart
            return cartRepository.save(existingCart);
        } else {
            // If the cart doesn't exist, create a new cart
            for (CartItem item : cart.getItems()) {
                // Associate the CartItem with the new Cart
                item.setCart(cart);
            }

            // Set the total amount for the new cart
            cart.setTotalAmount(calculateTotalAmount(cart.getItems()));

            // Save and return the newly created cart
            return cartRepository.save(cart);
        }
    }


    /**
     * Removes a product from the user's cart.
     *
     * @param userId    The ID of the user.
     * @param productId The ID of the product to be removed from the cart.
     * @return The updated cart after the product is removed.
     */
    @Override
    public Cart removeFromCart(Long userId, Long productId) {
        Cart cart = getCartByUserId(userId).get();

        // Remove the product from the cart items
        List<CartItem> updatedItems = cart.getItems();
        updatedItems.removeIf(item -> item.getProduct().getId().equals(productId));

        cart.setItems(updatedItems);
        cart.setTotalAmount(calculateTotalAmount(updatedItems));

        return cartRepository.save(cart);
    }

    /**
     * Clears all items from the user's cart.
     *
     * @param userId The ID of the user whose cart is to be cleared.
     * @return The cleared cart.
     */
    @Override
    public Cart clearCart(Long userId) {
        Cart cart = getCartByUserId(userId).get();
        cart.getItems().clear();
        cart.setTotalAmount(0.0);
        return cartRepository.save(cart);
    }

    /**
     * Updates the items in an existing cart by either adding new items or updating quantities.
     *
     * @param existingCart The existing cart to be updated.
     * @param newItems     The new items to be added or updated.
     */
    private void updateCartItems(Cart existingCart, List<CartItem> newItems) {
        for (CartItem newItem : newItems) {
            Optional<CartItem> existingItemOpt = existingCart.getItems().stream()
                    .filter(item -> item.getProduct().getId().equals(newItem.getProduct().getId()))
                    .findFirst();

            if (existingItemOpt.isPresent()) {
                // If the item exists, update its quantity and total price
                CartItem existingItem = existingItemOpt.get();

                // Directly set the quantity to avoid double incrementing
                existingItem.setQuantity(newItem.getQuantity());

                // Update the total price based on the new quantity
                existingItem.setTotalPrice(existingItem.getQuantity() * existingItem.getUnitPrice());
            } else {
                // If the item doesn't exist, add it to the cart
                newItem.setCart(existingCart);
                existingCart.getItems().add(newItem);
            }
        }
    }


    /**
     * Calculates the total amount of all items in the cart.
     *
     * @param items The list of cart items.
     * @return The total amount.
     */
    private double calculateTotalAmount(List<CartItem> items) {
        return items.stream()
                .mapToDouble(CartItem::getTotalPrice)
                .sum();
    }
}

