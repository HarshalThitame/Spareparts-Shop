package com.sparesparts.service;

import com.sparesparts.entity.Review;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing {@link Review} entities.
 */
public interface ReviewService {

    /**
     * Get reviews by product ID.
     *
     * @param productId The ID of the product.
     * @return A list of reviews for the given product.
     */
    List<Review> getReviewsByProductId(Long productId);

    /**
     * Save or update a review.
     *
     * @param review The review to save.
     * @return The saved review.
     */
    Review saveReview(Review review);

    /**
     * Delete a review by its ID.
     *
     * @param id The ID of the review.
     */
    void deleteReviewById(Long id);
}

