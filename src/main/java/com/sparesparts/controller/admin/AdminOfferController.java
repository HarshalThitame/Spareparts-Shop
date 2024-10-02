package com.sparesparts.controller.admin;

import com.sparesparts.entity.Offer;
import com.sparesparts.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/admin/offers")
public class AdminOfferController {


    private final OfferService offerService; // Service for managing offers

    public AdminOfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    /**
     * Retrieves all active offers currently valid.
     * @return List of active offers.
     */
    @GetMapping("/active")
    public List<Offer> getActiveOffers() {
        return offerService.getActiveOffers();
    }

    /**
     * Creates a new offer in the system.
     * @param offer The offer to be created.
     */
    @PostMapping
    public ResponseEntity<Offer> createOffer(@RequestBody Offer offer) {

        return ResponseEntity.ok().body(offerService.addOffer(offer));
    }

    /**
     * Applies an existing offer to a specific product.
     * @param offerId The ID of the offer to apply.
     * @param productId The ID of the product to which the offer will be applied.
     */
    @PostMapping("/{offerId}/products/{productId}")
    public void applyOfferToProduct(@PathVariable Long offerId, @PathVariable Long productId) {
        offerService.applyOfferToProduct(offerId, productId);
    }

    @PutMapping("/add-image")
    public ResponseEntity<Offer> addImageToOffer(@RequestBody Offer offer) {
        Offer existingOffer = offerService.getOfferById(offer.getId());
        if (existingOffer != null) {
            existingOffer.setImageUrl(offer.getImageUrl());
            return ResponseEntity.ok().body(offerService.addOffer(offer));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOffer(@PathVariable Long id) {
        Offer existingOffer = offerService.getOfferById(id);
        if (existingOffer != null) {
            offerService.deleteOffer(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.noContent().build();
    }
}
