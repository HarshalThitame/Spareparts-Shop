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

}
