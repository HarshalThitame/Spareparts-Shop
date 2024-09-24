package com.sparesparts.repositories;


import com.sparesparts.entity.Cart;
import com.sparesparts.entity.CartItem;
import com.sparesparts.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for managing {@link CartItem} entities.
 * Provides methods to handle items within a user's cart.
 */
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    /**
     * Find all items in a user's cart.
     *
     * @param cart The cart containing the items.
     * @return A list of cart items associated with the given cart.
     */
    List<CartItem> findByCart(Cart cart);

    /**
     * Find a specific item in the cart by its product.
     *
     * @param cart The cart in which the item is being searched.
     * @param product The product being searched.
     * @return The cart item associated with the given product.
     */
    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
}

