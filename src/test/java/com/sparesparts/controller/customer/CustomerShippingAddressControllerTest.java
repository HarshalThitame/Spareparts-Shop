package com.sparesparts.controller.customer;

import com.sparesparts.controller.customer.CustomerShippingAddressController;
import com.sparesparts.entity.ShippingAddress;
import com.sparesparts.service.ShippingAddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomerShippingAddressControllerTest {

    @Mock
    private ShippingAddressService shippingAddressService;

    @InjectMocks
    private CustomerShippingAddressController customerShippingAddressController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllShippingAddresses() {
        List<ShippingAddress> mockAddresses = Arrays.asList(new ShippingAddress(), new ShippingAddress());
        when(shippingAddressService.getAllShippingAddresses()).thenReturn(mockAddresses);

        ResponseEntity<List<ShippingAddress>> response = customerShippingAddressController.getAllShippingAddresses();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockAddresses, response.getBody());
        verify(shippingAddressService, times(1)).getAllShippingAddresses();
    }

    @Test
    void testGetShippingAddressById() {
        Long addressId = 1L;
        ShippingAddress mockAddress = new ShippingAddress();
        when(shippingAddressService.getShippingAddressById(addressId)).thenReturn(mockAddress);

        ResponseEntity<ShippingAddress> response = customerShippingAddressController.getShippingAddressById(addressId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockAddress, response.getBody());
        verify(shippingAddressService, times(1)).getShippingAddressById(addressId);
    }

    @Test
    void testAddShippingAddress() {
        ShippingAddress newAddress = new ShippingAddress();
        ShippingAddress addedAddress = new ShippingAddress();
        when(shippingAddressService.addShippingAddress(any(ShippingAddress.class))).thenReturn(addedAddress);

        ResponseEntity<ShippingAddress> response = customerShippingAddressController.addShippingAddress(newAddress);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(addedAddress, response.getBody());
        verify(shippingAddressService, times(1)).addShippingAddress(newAddress);
    }

    @Test
    void testUpdateShippingAddress() {
        Long addressId = 1L;
        ShippingAddress updatedAddress = new ShippingAddress();
        when(shippingAddressService.updateShippingAddress(any(ShippingAddress.class))).thenReturn(updatedAddress);

        ResponseEntity<ShippingAddress> response = customerShippingAddressController.updateShippingAddress(addressId, updatedAddress);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updatedAddress, response.getBody());
        verify(shippingAddressService, times(1)).updateShippingAddress(updatedAddress);
    }

    @Test
    void testDeleteShippingAddress() {
        Long addressId = 1L;

        ResponseEntity<String> response = customerShippingAddressController.deleteShippingAddress(addressId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Shipping address deleted successfully", response.getBody());
        verify(shippingAddressService, times(1)).deleteShippingAddress(addressId);
    }
}