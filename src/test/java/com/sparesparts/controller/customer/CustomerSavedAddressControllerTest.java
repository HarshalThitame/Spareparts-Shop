package com.sparesparts.controller.customer;

import com.sparesparts.controller.customer.CustomerSavedAddressController;
import com.sparesparts.entity.SavedAddress;
import com.sparesparts.entity.User;
import com.sparesparts.service.SavedAddressService;
import com.sparesparts.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomerSavedAddressControllerTest {

    @Mock
    private SavedAddressService savedAddressService;

    @Mock
    private UserService userService;

    @InjectMocks
    private CustomerSavedAddressController customerSavedAddressController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateSavedAddress() {
        Long userId = 1L;
        SavedAddress savedAddress = new SavedAddress();
        User user = new User();
        when(userService.findById(userId)).thenReturn(Optional.of(user));
        when(savedAddressService.saveAddress(any(SavedAddress.class))).thenReturn(savedAddress);

        ResponseEntity<SavedAddress> response = customerSavedAddressController.createSavedAddress(userId, savedAddress);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(savedAddress, response.getBody());
        verify(userService, times(1)).findById(userId);
        verify(savedAddressService, times(1)).saveAddress(savedAddress);
    }

    @Test
    void testGetAddressesByUserId() {
        Long userId = 1L;
        List<SavedAddress> mockAddresses = Arrays.asList(new SavedAddress(), new SavedAddress());
        when(savedAddressService.getAddressesByUserId(userId)).thenReturn(mockAddresses);

        ResponseEntity<List<SavedAddress>> response = customerSavedAddressController.getAddressesByUserId(userId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockAddresses, response.getBody());
        verify(savedAddressService, times(1)).getAddressesByUserId(userId);
    }

    @Test
    void testDeleteSavedAddress() {
        Long userId = 1L;
        Long addressId = 2L;

        ResponseEntity<Void> response = customerSavedAddressController.deleteSavedAddress(userId, addressId);

        assertEquals(204, response.getStatusCodeValue());
        verify(savedAddressService, times(1)).deleteAddress(addressId);
    }
}