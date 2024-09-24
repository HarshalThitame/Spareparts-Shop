package com.sparesparts.service.serviceImpl;


import com.sparesparts.entity.CartItem;
import com.sparesparts.repositories.CartItemRepository;
import com.sparesparts.repositories.CartRepository;
import com.sparesparts.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link CartItemService}.
 */
@Service
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;

    @Autowired
    public CartItemServiceImpl(CartItemRepository cartItemRepository, CartRepository cartRepository) {
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public List<CartItem> getCartItemsByCartId(Long cartId) {
        return cartRepository.findById(cartId)
                .map(cartItemRepository::findByCart)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found"));
    }

    @Override
    public CartItem saveCartItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    @Override
    public void deleteCartItemById(Long id) {
        cartItemRepository.deleteById(id);
    }
}
