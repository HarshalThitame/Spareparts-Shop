package com.sparesparts.controller.admin;

import com.sparesparts.customeexception.ResourceNotFoundException;
import com.sparesparts.entity.Category;
import com.sparesparts.entity.User;
import com.sparesparts.service.CategoryService;
import com.sparesparts.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST controller for handling admin-specific actions.
 * Only users with the ADMIN role can access these endpoints.
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;
    private final CategoryService categoryService;


    @Autowired
    public AdminController(UserService userService, CategoryService categoryService) {
        this.userService = userService;
        this.categoryService = categoryService;
    }

    /**
     * Get a list of all users.
     *
     * @return List of users.
     */
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /**
     * Get a user by ID.
     *
     * @param id The ID of the user.
     * @return The user with the given ID.
     */
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }
        return ResponseEntity.ok(user);
    }


    /**
     * Delete a user by ID.
     *
     * @param id The ID of the user to be deleted.
     * @return Confirmation message.
     */
    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok("User deleted successfully.");
    }
}

