package com.sparesparts.service;

import com.sparesparts.entity.ShippingAddress;

import java.util.List;

/**
 * Service interface for ShippingAddress entity.
 */
public interface ShippingAddressService {

    /**
     * Create a new shipping address.
     * @param shippingAddress The shipping address to create.
     * @return The created shipping address.
     */
    ShippingAddress createShippingAddress(ShippingAddress shippingAddress);

    /**
     * Get a shipping address by ID.
     * @param id The ID of the shipping address to retrieve.
     * @return The shipping address with the specified ID.
     */
    ShippingAddress getShippingAddressById(Long id);

    /**
     * Update an existing shipping address.
     * @param shippingAddress The shipping address with updated details.
     * @return The updated shipping address.
     */
    ShippingAddress updateShippingAddress(ShippingAddress shippingAddress);

    /**
     * Delete a shipping address by ID.
     * @param id The ID of the shipping address to delete.
     */
    void deleteShippingAddress(Long id);


    /**
     * Get all shipping addresses for the authenticated customer.
     * @return List of shipping addresses.
     */
    List<ShippingAddress> getAllShippingAddresses();


    /**
     * Add a new shipping address for the authenticated customer.
     * @param shippingAddress The shipping address details to be added.
     * @return The added shipping address.
     */
    ShippingAddress addShippingAddress(ShippingAddress shippingAddress);


}
