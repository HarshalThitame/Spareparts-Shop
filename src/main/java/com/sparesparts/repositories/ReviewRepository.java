package com.sparesparts.repositories;

import com.sparesparts.entity.Product;
import com.sparesparts.entity.Review;
import com.sparesparts.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for managing {@link Review} entities.
 * Provides methods for handling product reviews.
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    /**
     * Find all reviews for a specific product.
     *
     * @param product The product for which reviews are being retrieved.
     * @return A list of reviews for the given product.
     */
    List<Review> findByProduct(Product product);

    /**
     * Find all reviews written by a specific user.
     *
     * @param user The user who wrote the reviews.
     * @return A list of reviews written by the user.
     */
    List<Review> findByUser(User user);
}

