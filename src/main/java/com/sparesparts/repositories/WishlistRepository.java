package com.sparesparts.repositories;

import com.sparesparts.entity.User;
import com.sparesparts.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for managing {@link Wishlist} entities.
 * Provides methods for handling a user's wishlist.
 */
@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    /**
     * Find the wishlist by the associated user.
     *
     * @param user The user whose wishlist is being searched.
     * @return The wishlist associated with the user.
     */
    Optional<Wishlist> findByUser(User user);

    /**
     * Check if a user has an existing wishlist.
     *
     * @param user The user to check.
     * @return True if the user has a wishlist, otherwise false.
     */
    boolean existsByUser(User user);
}

