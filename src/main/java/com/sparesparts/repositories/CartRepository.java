package com.sparesparts.repositories;

import com.sparesparts.entity.Cart;
import com.sparesparts.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for managing {@link Cart} entities.
 * Provides methods for handling user carts.
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    /**
     * Find a cart by the associated user.
     *
     * @param user The user whose cart is being searched.
     * @return The cart associated with the user.
     */
    Optional<Cart> findByUser(User user);

    /**
     * Check if a user has an existing cart.
     *
     * @param user The user to check.
     * @return True if the user has a cart, otherwise false.
     */
    boolean existsByUser(User user);

    Optional<Cart> findByUserId(Long id);
}

