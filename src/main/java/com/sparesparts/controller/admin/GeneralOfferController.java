package com.sparesparts.controller.admin;

import com.sparesparts.entity.Offer;
import com.sparesparts.entity.Product;
import com.sparesparts.service.OfferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/general/offers")
public class GeneralOfferController {


    private final OfferService offerService; // Service for managing offers

    public GeneralOfferController(OfferService offerService) {
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

    @GetMapping("/products/{id}")
    public ResponseEntity<List<Product>> getProducts(@PathVariable Long id) {
        List<Product> products = offerService.getProductsByOfferId(id);
        return ResponseEntity.ok(products);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Offer> getProduct(@PathVariable Long id) {
        Offer offer = offerService.getOfferById(id);
        if (offer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(offer);
    }
}
