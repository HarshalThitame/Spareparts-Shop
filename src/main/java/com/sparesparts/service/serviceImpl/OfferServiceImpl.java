package com.sparesparts.service.serviceImpl;

import com.sparesparts.entity.Offer;
import com.sparesparts.entity.Product;
import com.sparesparts.repositories.ProductRepository;
import com.sparesparts.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sparesparts.repository.OfferRepository;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OfferServiceImpl implements OfferService {

    @Autowired
    private OfferRepository offerRepository; // Repository for managing offers

    @Autowired
    private ProductRepository productRepository; // Repository for managing products

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Offer> getActiveOffers() {
        LocalDateTime now = LocalDateTime.now();  // Get the current time
        return offerRepository.findByStartDateBeforeAndEndDateAfter(now, now); // Pass current time for both start and end comparison
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Offer addOffer(Offer offer) {
        // Save the provided offer to the database
        return offerRepository.save(offer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void applyOfferToProduct(Long offerId, Long productId) {
        // Retrieve the offer and product from the database using their IDs
        Offer offer = offerRepository.findById(offerId).orElseThrow(() -> new RuntimeException("Offer not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        // Associate the offer with the product
        offer.getProducts().add(product);
        product.getOffers().add(offer); // Assuming you have a Set<Offer> offers in Product entity

        // Save the updated offer to persist the changes
        offerRepository.save(offer);
    }
}
