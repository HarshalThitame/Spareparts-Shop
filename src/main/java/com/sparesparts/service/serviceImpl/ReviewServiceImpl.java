package com.sparesparts.service.serviceImpl;
import com.sparesparts.entity.Review;
import com.sparesparts.repositories.ProductRepository;
import com.sparesparts.repositories.ReviewRepository;
import com.sparesparts.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link ReviewService}.
 */
@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<Review> getReviewsByProductId(Long productId) {
        return productRepository.findById(productId)
                .map(reviewRepository::findByProduct)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

    @Override
    public Review saveReview(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public void deleteReviewById(Long id) {
        reviewRepository.deleteById(id);
    }
}

