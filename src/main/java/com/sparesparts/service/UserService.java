package com.sparesparts.service;


import com.sparesparts.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
    User findByEmail(String email);
    User createUser(User user);
    void deleteById(Long id);
    List<User> getAllUsers();


    User updateUserProfile(String username, User userDetails);

    User getCurrentUser();

    User getUserById(Long userId);

    User updateUser(User newUser);

    User findByMobile(String mobile);

    void deleteUserById(Long id);
}
