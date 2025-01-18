package com.sparesparts.controller.machanic;

import com.sparesparts.controller.machanic.MechanicCartController;
import com.sparesparts.entity.Cart;
import com.sparesparts.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MechanicCartControllerTest {

    @Mock
    private CartService cartService;

    @InjectMocks
    private MechanicCartController mechanicCartController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddOrUpdateCart() {
        Cart cart = new Cart();
        Cart updatedCart = new Cart();
        when(cartService.addOrUpdateCart(any(Cart.class))).thenReturn(updatedCart);

        ResponseEntity<Cart> response = mechanicCartController.addOrUpdateCart(cart);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updatedCart, response.getBody());
        verify(cartService, times(1)).addOrUpdateCart(cart);
    }

    @Test
    void testGetCart() {
        Long userId = 1L;
        Cart cart = new Cart();
        when(cartService.getCartByUserId(userId)).thenReturn(Optional.of(cart));

        ResponseEntity<Cart> response = mechanicCartController.getCart(userId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(cart, response.getBody());
        verify(cartService, times(1)).getCartByUserId(userId);
    }

    @Test
    void testRemoveFromCart() {
        Long userId = 1L;
        Long productId = 2L;
        Cart updatedCart = new Cart();
        when(cartService.removeFromCart(userId, productId)).thenReturn(updatedCart);

        ResponseEntity<Cart> response = mechanicCartController.removeFromCart(userId, productId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updatedCart, response.getBody());
        verify(cartService, times(1)).removeFromCart(userId, productId);
    }

    @Test
    void testClearCart() {
        Long userId = 1L;
        Cart clearedCart = new Cart();
        when(cartService.clearCart(userId)).thenReturn(clearedCart);

        ResponseEntity<Cart> response = mechanicCartController.clearCart(userId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(clearedCart, response.getBody());
        verify(cartService, times(1)).clearCart(userId);
    }
}