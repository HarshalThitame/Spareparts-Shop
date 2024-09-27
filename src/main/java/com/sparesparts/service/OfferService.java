package com.sparesparts.service;

import com.sparesparts.entity.Offer;

import java.util.List;

public interface OfferService {

    /**
     * Retrieves a list of active offers that are currently valid based on the current date and time.
     * @return List of active offers.
     */
    List<Offer> getActiveOffers();

    /**
     * Adds a new offer to the system.
     * @param offer The offer to be added.
     */
    Offer addOffer(Offer offer);

    /**
     * Applies an existing offer to a specific product.
     * This method links the offer and the product in the database.
     * @param offerId The ID of the offer to apply.
     * @param productId The ID of the product to which the offer will be applied.
     */
    void applyOfferToProduct(Long offerId, Long productId);
}
