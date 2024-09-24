package com.sparesparts.service;

import com.sparesparts.entity.Wishlist;

import java.util.Optional;

/**
 * Service interface for managing {@link Wishlist} entities.
 */
public interface WishlistService {

    /**
     * Get wishlist by user ID.
     *
     * @param userId The ID of the user.
     * @return The wishlist of the given user.
     */
    Optional<Wishlist> getWishlistByUserId(Long userId);

    /**
     * Save or update a wishlist.
     *
     * @param wishlist The wishlist to save.
     * @return The saved wishlist.
     */
    Wishlist saveWishlist(Wishlist wishlist);

    /**
     * Delete a wishlist by its ID.
     *
     * @param id The ID of the wishlist.
     */
    void deleteWishlistById(Long id);
}
