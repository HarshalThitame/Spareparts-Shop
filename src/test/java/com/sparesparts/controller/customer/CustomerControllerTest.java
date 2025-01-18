package com.sparesparts.controller.customer;

import com.sparesparts.controller.customer.CustomerController;
import com.sparesparts.entity.Wishlist;
import com.sparesparts.service.CartService;
import com.sparesparts.service.WishlistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomerControllerTest {

    @Mock
    private CartService cartService;

    @Mock
    private WishlistService wishlistService;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    void testDeleteWishlistItem() {
        Long wishlistId = 1L;

        ResponseEntity<String> response = customerController.deleteWishlistItem(wishlistId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Wishlist item removed successfully.", response.getBody());
        verify(wishlistService, times(1)).deleteWishlistById(wishlistId);
    }
}