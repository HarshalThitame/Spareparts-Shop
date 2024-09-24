package com.sparesparts.service.serviceImpl;


import com.sparesparts.entity.User;
import com.sparesparts.entity.Wishlist;
import com.sparesparts.repositories.UserRepository;
import com.sparesparts.repositories.WishlistRepository;
import com.sparesparts.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of the {@link WishlistService}.
 */
@Service
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;

    @Autowired
    public WishlistServiceImpl(WishlistRepository wishlistRepository, UserRepository userRepository) {
        this.wishlistRepository = wishlistRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<Wishlist> getWishlistByUserId(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.flatMap(wishlistRepository::findByUser);
    }

    @Override
    public Wishlist saveWishlist(Wishlist wishlist) {
        return wishlistRepository.save(wishlist);
    }

    @Override
    public void deleteWishlistById(Long id) {
        wishlistRepository.deleteById(id);
    }
}
