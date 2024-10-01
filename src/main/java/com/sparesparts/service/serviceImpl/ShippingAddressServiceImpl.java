package com.sparesparts.service.serviceImpl;


import com.sparesparts.entity.ShippingAddress;
import com.sparesparts.repositories.ShippingAddressRepository;
import com.sparesparts.service.ShippingAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the ShippingAddressService interface.
 */
@Service
public class ShippingAddressServiceImpl implements ShippingAddressService {

    private final ShippingAddressRepository shippingAddressRepository;

    @Autowired
    public ShippingAddressServiceImpl(ShippingAddressRepository shippingAddressRepository) {
        this.shippingAddressRepository = shippingAddressRepository;
    }

    /**
     * Create a new shipping address.
     * @param shippingAddress The shipping address to create.
     * @return The created shipping address.
     */
    @Override
    public ShippingAddress createShippingAddress(ShippingAddress shippingAddress) {
        if (shippingAddressRepository.existsById(shippingAddress.getId())) {
        return shippingAddressRepository.save(shippingAddress);

        }else {
            shippingAddress.setId(System.currentTimeMillis()-10000000);
            return shippingAddressRepository.save(shippingAddress);
        }
    }

    /**
     * Get a shipping address by ID.
     * @param id The ID of the shipping address to retrieve.
     * @return The shipping address with the specified ID.
     */
    @Override
    public ShippingAddress getShippingAddressById(Long id) {
        return shippingAddressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ShippingAddress not found with ID: " + id));
    }

    /**
     * Update an existing shipping address.
     * @param shippingAddress The shipping address with updated details.
     * @return The updated shipping address.
     */
    @Override
    public ShippingAddress updateShippingAddress(ShippingAddress shippingAddress) {
        // Check if shipping address exists
        if (!shippingAddressRepository.existsById(shippingAddress.getId())) {
            throw new RuntimeException("ShippingAddress not found with ID: " + shippingAddress.getId());
        }
        return shippingAddressRepository.save(shippingAddress);
    }

    /**
     * Delete a shipping address by ID.
     * @param id The ID of the shipping address to delete.
     */
    @Override
    public void deleteShippingAddress(Long id) {
        if (!shippingAddressRepository.existsById(id)) {
            throw new RuntimeException("ShippingAddress not found with ID: " + id);
        }
        shippingAddressRepository.deleteById(id);
    }


    @Override
    public List<ShippingAddress> getAllShippingAddresses() {
        return shippingAddressRepository.findAll();
    }

    @Override
    public ShippingAddress addShippingAddress(ShippingAddress shippingAddress) {
        if (shippingAddressRepository.existsById(shippingAddress.getId())) {
            return shippingAddressRepository.save(shippingAddress);

        }else {
            shippingAddress.setId(System.currentTimeMillis()-10000000);
            return shippingAddressRepository.save(shippingAddress);
        }
    }


}
