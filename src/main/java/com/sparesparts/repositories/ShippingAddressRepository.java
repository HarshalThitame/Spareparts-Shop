package com.sparesparts.repositories;


import com.sparesparts.entity.ShippingAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for ShippingAddress entity.
 */
@Repository
public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, Long> {

    /**
     * Find a shipping address by user ID.
     * @param userId The ID of the user.
     * @return The shipping address associated with the specified user.
     */
    List<ShippingAddress> findByUserId(Long userId);
    ShippingAddress findByAddressLine1AndCityAndStateAndPostalCode(String addressLine1, String city, String state, String postalCode);

}
